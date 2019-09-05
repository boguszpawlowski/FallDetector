package com.bpawlowski.data.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.model.Event

interface EventRepository {
    val allEvents: LiveData<List<Event>>
    suspend fun syncEvents()
	suspend fun updateAttending(event: Event?): Result<Event>
    suspend fun getEvent(eventId: Long): Result<Event>
    suspend fun addEvent(event: Event): Result<Unit>
    suspend fun updateEvent(event: Event): Result<Unit>
    suspend fun removeEvent(event: Event): Result<Unit>
}