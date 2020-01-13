package com.bpawlowski.domain.repository

import com.bpawlowski.domain.Result
import com.bpawlowski.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
    suspend fun syncEvents()
    suspend fun updateAttending(event: Event?): Result<Event>
    suspend fun getEvent(eventId: Long): Result<Event>
    suspend fun addEvent(event: Event): Result<Unit>
    suspend fun updateEvent(event: Event): Result<Unit>
    suspend fun removeEvent(event: Event): Result<Unit>
}
