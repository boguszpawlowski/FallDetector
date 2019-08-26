package com.bpawlowski.database.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

inline fun <T, R : Comparable<R>> Flow<List<T>>.sortedBy(crossinline selector: (T) -> R?) = flow {
	collect { emit(it.sortedBy(selector)) }
}

inline fun <T, R : Comparable<R>> Flow<List<T>>.sortedByDescending(crossinline selector: (T) -> R?) = flow {
	collect { emit(it.sortedByDescending(selector)) }
}

suspend fun <T> MutableLiveData<T>.fromFlow(flow: Flow<T>) {
	flow.collect { postValue(it) }
}