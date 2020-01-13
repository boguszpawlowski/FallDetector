package com.example.bpawlowski.falldetector.domain

import com.bpawlowski.domain.exception.FallDetectorException

sealed class StateValue<out T> {
    object Loading : StateValue<Nothing>()
    object Uninitialized : StateValue<Nothing>()
    data class Success<out T>(val data: T) : StateValue<T>()
    data class Failure(val exception: FallDetectorException) : StateValue<Nothing>()

    operator fun invoke(): T? = (this as? Success)?.data
}

fun <T> success(data: T) = StateValue.Success(data)

fun loading() = StateValue.Loading

fun failure(exception: FallDetectorException) = StateValue.Failure(exception)

fun <T> StateValue<T>.onSuccess(block: (T) -> Unit): StateValue<T> {
    if (this is StateValue.Success) {
        block(data)
    }
    return this
}

fun <T> StateValue<T>.onFailure(block: (FallDetectorException) -> Unit): StateValue<T> {
    if (this is StateValue.Failure) {
        block(exception)
    }
    return this
}
