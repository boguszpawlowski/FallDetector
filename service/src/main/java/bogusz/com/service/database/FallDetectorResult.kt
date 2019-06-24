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

inline fun <T, R> FallDetectorResult<T>.map(block: (T) -> R): FallDetectorResult<R> =
    when (this) {
        is FallDetectorResult.Success -> success(block(data))
        is FallDetectorResult.Failure -> this
    }

inline fun <T, R> FallDetectorResult<T>.flatMap(block: (T) -> FallDetectorResult<R>): FallDetectorResult<R> =
    when (this) {
        is FallDetectorResult.Success -> block(data)
        is FallDetectorResult.Failure -> this
    }

inline fun <T, R> FallDetectorResult<T>.fold(onSuccess: (T) -> R, onFailure: (Exception) -> R): FallDetectorResult<T> {
    when (this) {
        is FallDetectorResult.Success -> onSuccess(data)
        is FallDetectorResult.Failure -> onFailure(error)
    }
    return this
}

inline fun <T, R, D> zip(
    first: FallDetectorResult<T>,
    second: FallDetectorResult<D>,
    transform: (T, D) -> R
): FallDetectorResult<R> =
    when {
        first is FallDetectorResult.Success && second is FallDetectorResult.Success ->
            success(transform(first.data, second.data))
        first is FallDetectorResult.Success && second is FallDetectorResult.Failure ->
            second
        first is FallDetectorResult.Failure && second is FallDetectorResult.Success ->
            first
        else -> failure(RuntimeException("Both functions failed"))
    }

/**
 * Failure operators
 */
inline fun <T> FallDetectorResult<T>.onFailure(block: (Exception) -> Unit): FallDetectorResult<T> {
    if (this is FallDetectorResult.Failure) block(error)
    return this
}

/**
 * Get nullable value
 */
fun <T> FallDetectorResult<T>.getOrNull(): T? =
    (this as? FallDetectorResult.Success)?.data

fun <T> FallDetectorResult<T>.getOrThrow(): T? =
    when (this) {
        is FallDetectorResult.Success -> data
        is FallDetectorResult.Failure -> throw error
    }

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
