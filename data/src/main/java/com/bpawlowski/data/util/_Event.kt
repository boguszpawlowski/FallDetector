package com.bpawlowski.data.util

import com.bpawlowski.core.model.Event
import com.bpawlowski.database.entity.EventDb

fun EventDb.toDomain() = Event(
    id = id,
    title = title,
    creatorId = creatorId,
    location = location,
    date = date,
    remoteId = remoteId
)

fun Event.toEntity() = EventDb(
    id = id,
    title = title,
    creatorId = creatorId,
    location = location,
    date = date,
    remoteId = remoteId
)