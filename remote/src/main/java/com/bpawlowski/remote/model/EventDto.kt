package com.bpawlowski.remote.model

import com.bpawlowski.domain.model.Event
import com.google.gson.annotations.SerializedName

data class EventDto(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String,
    @SerializedName("creator_id") val creatorId: Long,
    @SerializedName("date") val date: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("attending") val attending: Int,
    @SerializedName("creator_mobile") val creatorMobile: String
)

internal fun EventDto.toDomain() = Event(
    title = title,
    creatorId = creatorId,
    latLang = latitude to longitude,
    date = date,
    remoteId = id,
    attendingUsers = attending,
    isAttending = false,
    creatorMobile = creatorMobile
)

internal fun Event.toDto() = EventDto(
    id = remoteId,
    title = title,
    creatorId = creatorId,
    latitude = latLang.first,
    longitude = latLang.second,
    date = date,
    attending = attendingUsers,
    creatorMobile = creatorMobile
)
