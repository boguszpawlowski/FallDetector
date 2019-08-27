package com.bpawlowski.database.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform

@ExperimentalCoroutinesApi
inline fun <T, R : Comparable<R>> Flow<List<T>>.sortedBy(crossinline selector: (T) -> R?) = transform {
	emit(it.sortedBy(selector))
}

@ExperimentalCoroutinesApi
inline fun <T, R : Comparable<R>> Flow<List<T>>.sortedByDescending(crossinline selector: (T) -> R?) = transform {
	emit(it.sortedByDescending(selector))
}

suspend fun <T> MutableLiveData<T>.fromFlow(flow: Flow<T>) {
	flow.collect { postValue(it) }
}