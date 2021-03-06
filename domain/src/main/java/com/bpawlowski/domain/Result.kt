package com.bpawlowski.domain

import com.bpawlowski.domain.exception.FallDetectorException

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val error: Exception) : Result<Nothing>()

    inline fun onSuccess(block: (T) -> Unit): Result<T> {
        if (this is Success) block(data)
        return this
    }

    inline fun <R> map(block: (T) -> R): Result<R> =
        when (this) {
            is Success -> success(block(data))
            is Failure -> this
        }

    inline fun <R> flatMap(block: (T) -> Result<R>): Result<R> =
        when (this) {
            is Success -> block(data)
            is Failure -> this
        }

    inline fun <R> fold(onSuccess: (T) -> R, onFailure: (Exception) -> R): Result<T> {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }
        return this
    }

    inline fun onException(block: (Exception) -> Unit): Result<T> {
        if (this is Failure) block(error)
        return this
    }

    inline fun onFailure(block: (FallDetectorException) -> Unit): Result<T> {
        if (this is Failure) {
            if (error is FallDetectorException) {
                block(error)
            } else {
                throw error
            }
        }
        return this
    }

    fun getOrNull(): T? = (this as? Success)?.data

    fun getOrThrow(): T =
        when (this) {
            is Success -> data
            is Failure -> throw error
        }
}

fun <T, D> zip(
    first: Result<T>,
    second: Result<D>
): Result<Pair<T, D>> =
    when {
        first is Result.Success && second is Result.Success ->
            success(first.data to second.data)
        first is Result.Success && second is Result.Failure ->
            second
        first is Result.Failure && second is Result.Success ->
            first
        else -> failure(RuntimeException("Both functions failed"))
    }

fun <T> success(data: T) = Result.Success(data)

fun failure(e: Exception) = Result.Failure(e)
