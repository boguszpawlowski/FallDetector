package com.bpawlowski.core.model

data class Event(
	val id: Long? = null,
	val title: String,
	val creatorId: Long,
	val latLang: Pair<Double, Double>,
	val date: String,
	val remoteId: Long?
){

	override fun hashCode(): Int {
		var result = title.hashCode()
		result = 31 * result + creatorId.hashCode()
		result = 31 * result + latLang.hashCode()
		result = 31 * result + date.hashCode()
		result = 31 * result + (remoteId?.hashCode() ?: 0)
		return result
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Event

		if (title != other.title) return false
		if (creatorId != other.creatorId) return false
		if (latLang != other.latLang) return false
		if (date != other.date) return false
		if (remoteId != other.remoteId) return false

		return true
	}
}