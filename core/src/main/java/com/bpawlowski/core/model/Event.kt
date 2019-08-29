package com.bpawlowski.core.model

data class Event(
	val id: Long? = null,
	val title: String,
	val creatorId: Long,
	val latLang: Pair<Double, Double>,
	val date: String,
	val remoteId: Long?
)