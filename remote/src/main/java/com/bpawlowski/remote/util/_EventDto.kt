package com.bpawlowski.remote.util

import com.bpawlowski.core.model.Event
import com.bpawlowski.remote.model.EventDto

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
