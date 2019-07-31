package com.example.bpawlowski.falldetector.util

import android.util.SparseArray

/** Returns true if the collection contains [key]. */
inline operator fun <T> SparseArray<T>.contains(key: Int) = indexOfKey(key) >= 0

/** Allows the use of the index operator for storing values in the collection. */
inline operator fun <T> SparseArray<T>.set(key: Int, value: T) = put(key, value)

/** Creates a new collection by adding or replacing entries from [other]. */
operator fun <T> SparseArray<T>.plus(other: SparseArray<T>): SparseArray<T> {
	val new = SparseArray<T>(size() + other.size())
	new.putAll(this)
	new.putAll(other)
	return new
}

/** Update this collection by adding or replacing entries from [other]. */
fun <T> SparseArray<T>.putAll(other: SparseArray<T>) = other.forEach(::put)

/** Performs the given [action] for each key/value entry. */
inline fun <T> SparseArray<T>.forEach(action: (key: Int, value: T) -> Unit) {
	for (index in 0 until size()) {
		action(keyAt(index), valueAt(index))
	}
}