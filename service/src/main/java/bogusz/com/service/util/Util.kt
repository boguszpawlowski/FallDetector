package bogusz.com.service.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

val doNothing = Unit

fun <T, R> LiveData<T>.map(transform: (T) -> R): LiveData<R> =
    Transformations.map(this) { transform.invoke(it) }

fun <T, R> LiveData<T>.switchMap(transform: (T) -> LiveData<R>): LiveData<R> =
    Transformations.switchMap(this) { transform.invoke(it) }

inline fun <T, R: Comparable<R>> LiveData<List<T>>.sortedBy(crossinline selector: (T) -> R?): LiveData<List<T>> =
    map { it.sortedWith(compareBy(selector)) }