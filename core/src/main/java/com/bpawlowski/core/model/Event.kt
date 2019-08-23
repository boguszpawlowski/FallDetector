package com.bpawlowski.core.model

data class Event(
	val id: Long? = null,
	val title: String,
	val creatorId: Long,
	val location: String,
	val date: String,
	val remoteId: Long?
)