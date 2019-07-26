package com.example.bpawlowski.falldetector.domain

import bogusz.com.service.database.exceptions.FallDetectorException

sealed class ScreenState<out T> {
	object Loading : ScreenState<Nothing>()
	data class Success<out T>(val data: T) : ScreenState<T>()
	data class Failure(val exception: FallDetectorException) : ScreenState<Nothing>()

	inline fun onSuccess(block: (T) -> Unit): ScreenState<T> {
		if (this is Success) block(data)
		return this
	}

	inline fun onFailure(block: (FallDetectorException) -> Unit): ScreenState<T> {
		if (this is Failure) block(exception)
		return this
	}

	inline fun onLoading(block: () -> Unit): ScreenState<T> {
		if (this is Loading) block()
		return this
	}
}