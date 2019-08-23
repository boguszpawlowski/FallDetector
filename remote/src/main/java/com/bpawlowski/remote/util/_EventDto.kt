package com.bpawlowski.remote.util

import com.bpawlowski.core.model.Event
import com.bpawlowski.remote.model.EventDto

internal fun EventDto.toDomain() = Event(
    title = title,
    creatorId = creatorId,
    location = location,
    date = date,
    remoteId = id
)

internal fun Event.toDto() = EventDto(
    id = remoteId,
    title = title,
    creatorId = creatorId,
    location = location,
    date = date
)
