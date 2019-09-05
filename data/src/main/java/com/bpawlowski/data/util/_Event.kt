package com.bpawlowski.data.util

import com.bpawlowski.core.model.Event
import com.bpawlowski.database.entity.EventDb

fun EventDb.toDomain() = Event(
    id = id,
    title = title,
    creatorId = creatorId,
    latLang = latitude.toDouble() to longitude.toDouble(),
    date = date,
    remoteId = remoteId,
	attendingUsers = attending,
	isAttending = isAttending
)

fun Event.toEntity() = EventDb(
    id = id,
    title = title,
	creatorId = creatorId,
	latitude = latLang.first.toString(),
	longitude = latLang.second.toString(),
    date = date,
    remoteId = remoteId,
	attending = attendingUsers,
	isAttending = isAttending
)