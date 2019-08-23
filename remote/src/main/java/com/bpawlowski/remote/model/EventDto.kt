package com.bpawlowski.remote.model

import com.google.gson.annotations.SerializedName

data class EventDto(
	@SerializedName("id") val id: Long? = null,
	@SerializedName("title") val title: String,
	@SerializedName("creator_id") val creatorId: Long,
	@SerializedName("location") val location: String,
	@SerializedName("date") val date: String //todo yodatime
)