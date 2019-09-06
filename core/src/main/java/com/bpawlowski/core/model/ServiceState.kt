package com.bpawlowski.core.model

data class ServiceState(
	val id: Long? = null,
	val isRunning: Boolean,
	val sensitivity: Sensitivity,
	val sendingSms: Boolean,
	val sendingLocation: Boolean
)