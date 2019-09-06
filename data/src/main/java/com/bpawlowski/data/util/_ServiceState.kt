package com.bpawlowski.data.util

import com.bpawlowski.core.model.ServiceState
import com.bpawlowski.database.entity.ServiceStateDb

fun ServiceStateDb.toDomain() = ServiceState(
	id = id,
	isRunning = isRunning,
	sensitivity = sensitivity,
	sendingSms = sendingSms,
	sendingLocation = sendingLocation
)

fun ServiceState.toEntity() = ServiceStateDb(
	id = id,
	isRunning = isRunning,
	sensitivity = sensitivity,
	sendingSms = sendingSms,
	sendingLocation = sendingLocation
)
