package com.bpawlowski.remote.client

import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.model.Event
import com.bpawlowski.remote.model.EventDto

interface EventClient {
    suspend fun getEvents(): List<Event>
    suspend fun postEvent(event: Event): Result<Long>
    suspend fun putEvent(event: Event): Result<Unit>
    suspend fun deleteEvent(event: Event): Result<Unit>
}