package com.example.bpawlowski.falldetector.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(transform: (T) -> R): LiveData<R> =
	Transformations.map(this) { transform.invoke(it) }

fun <T, R> LiveData<List<T>>.mapList(transform: (T) -> R): LiveData<List<R>> =
	Transformations.map(this) { it.map(transform) }