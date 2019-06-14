package bogusz.com.service.database

sealed class FallDetectorResult<out T> {
    data class Success<out T>(val data: T) : FallDetectorResult<T>()
    data class Failure(val error: Exception) : FallDetectorResult<Nothing>()
}

/**
 * Success operators
 */
inline fun <T> FallDetectorResult<T>.onSuccess(block: (T) -> Unit): FallDetectorResult<T> {
    if (this is FallDetectorResult.Success) block(data)
    return this
}

inline fun <T, R> FallDetectorResult<T>.map(block: (T) -> R): R? =
    if (this is FallDetectorResult.Success) block(data)
    else null

inline fun <T, R> FallDetectorResult<T>.flatMap(block: (T) -> FallDetectorResult<R>): FallDetectorResult<R> =
    when(this) {
        is FallDetectorResult.Success -> block(data)
        is FallDetectorResult.Failure -> this
    }

/**
 * Failure operators
 */
inline fun <T> FallDetectorResult<T>.onFailure(block: (Exception) -> Unit): FallDetectorResult<T> {
    if (this is FallDetectorResult.Failure) block(error)
    return this
}

inline fun <T, R> FallDetectorResult<T>.mapFailure(block: (Exception) -> R): R? =
    if (this is FallDetectorResult.Failure) block(error)
    else null

/**
 * Get nullable values
 */
val <T> FallDetectorResult<T>.success: T?
    get() = (this as? FallDetectorResult.Success)?.data

val <T> FallDetectorResult<T>.error: Exception?
    get() = (this as? FallDetectorResult.Failure)?.error

/**
 * Builders
 */
fun <T> success(data: T) = FallDetectorResult.Success(data)

fun failure(e: Exception) = FallDetectorResult.Failure(e)

inline fun <T> catching(block: () -> FallDetectorResult<T>): FallDetectorResult<T> =
    try {
        block()
    } catch (e: Exception) {
        failure(e)
    }
