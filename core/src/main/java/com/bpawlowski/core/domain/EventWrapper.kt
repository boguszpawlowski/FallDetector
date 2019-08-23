package com.bpawlowski.core.domain

data class EventWrapper<out T>(private val content: T) {

	var hasBeenHandled = false
		private set

	val value: T?
		@Synchronized
		get() = if (hasBeenHandled) {
			null
		} else {
			hasBeenHandled = true
			content
		}

	fun peekContent(): T = content
}