package com.example.bpawlowski.falldetector.domain

import androidx.lifecycle.MutableLiveData
import com.bpawlowski.core.exception.FallDetectorException

sealed class ScreenResult<out T> {
	object Loading : ScreenResult<Nothing>()
	object Loaded : ScreenResult<Nothing>()
	data class Success<out T>(val data: T) : ScreenResult<T>()
	data class Failure(val exception: FallDetectorException) : ScreenResult<Nothing>()
}

data class ScreenState<out T>(
	val loading: Boolean = false,
	val data: T? = null,
	val error: FallDetectorException? = null
)

fun <T> MutableLiveData<ScreenState<T>>.reduce(screenResult: ScreenResult<T>) {
	val temp = value ?: ScreenState()
	postValue(
		when (screenResult) {
			is ScreenResult.Loading -> temp.copy(loading = true, data = null, error = null)
			is ScreenResult.Loaded -> temp.copy(loading = false, data = null, error = null)
			is ScreenResult.Failure -> temp.copy(loading = false, data = null, error = screenResult.exception)
			is ScreenResult.Success -> temp.copy(loading = false, data = screenResult.data, error = null)
		}
	)
}