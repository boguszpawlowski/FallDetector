package bogusz.com.service.database

import bogusz.com.service.database.exceptions.FallDetectorException
import java.io.IOException

sealed class FallDetectorResult<out T> {
	data class Success<out T>(val data: T) : FallDetectorResult<T>()
	data class Failure(val error: Exception) : FallDetectorResult<Nothing>()

	/**
	 * Success operators
	 */
	inline fun onSuccess(block: (T) -> Unit): FallDetectorResult<T> {
		if (this is Success) block(data)
		return this
	}

	inline fun <R> map(block: (T) -> R): FallDetectorResult<R> =
		when (this) {
			is Success -> success(block(data))
			is Failure -> this
		}

	inline fun <R> flatMap(block: (T) -> FallDetectorResult<R>): FallDetectorResult<R> =
		when (this) {
			is Success -> block(data)
			is Failure -> this
		}

	inline fun <R> fold(onSuccess: (T) -> R, onFailure: (Exception) -> R): FallDetectorResult<T> {
		when (this) {
			is Success -> onSuccess(data)
			is Failure -> onFailure(error)
		}
		return this
	}

	/**
	 * Failure operators
	 */
	inline fun onException(block: (Exception) -> Unit): FallDetectorResult<T> {
		if (this is Failure) block(error)
		return this
	}

	inline fun onFailure(block: (FallDetectorException) -> Unit): FallDetectorResult<T> {
		if (this is Failure && error is FallDetectorException) block(error)
		return this
	}

	/**
	 * Get nullable value
	 */
	fun getOrNull(): T? =
		(this as? Success)?.data

	fun getOrThrow(): T? =
		when (this) {
			is Success -> data
			is Failure -> throw error
		}

}

fun <T, D> zip(
	first: FallDetectorResult<T>,
	second: FallDetectorResult<D>
): FallDetectorResult<Pair<T, D>> =
	when {
		first is FallDetectorResult.Success && second is FallDetectorResult.Success ->
			success(first.data to second.data)
		first is FallDetectorResult.Success && second is FallDetectorResult.Failure ->
			second
		first is FallDetectorResult.Failure && second is FallDetectorResult.Success ->
			first
		else -> failure(RuntimeException("Both functions failed"))
	}
/**
 * builders
 */

fun <T> success(data: T) = FallDetectorResult.Success(data)

fun failure(e: Exception) = FallDetectorResult.Failure(e)

inline fun <T> catchIO(block: () -> FallDetectorResult<T>): FallDetectorResult<T> =
	try {
		block()
	} catch (e: IOException) {
		failure(e)
	}
