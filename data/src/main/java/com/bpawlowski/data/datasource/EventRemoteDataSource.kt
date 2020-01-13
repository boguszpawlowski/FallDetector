package com.bpawlowski.data.datasource

import com.bpawlowski.domain.Result
import com.bpawlowski.domain.model.Event

interface EventRemoteDataSource {
    suspend fun getEvents(): Result<List<Event>>
    suspend fun postEvent(event: Event): Result<Long>
    suspend fun putEvent(event: Event): Result<Unit>
    suspend fun deleteEvent(event: Event): Result<Unit>
}
