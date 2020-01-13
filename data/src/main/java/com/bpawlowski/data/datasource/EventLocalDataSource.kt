package com.bpawlowski.data.datasource

import com.bpawlowski.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventLocalDataSource {
    fun getAllFlow(): Flow<List<Event>>
    suspend fun getAll(): List<Event>
    suspend fun getById(eventId: Long): Event?
    suspend fun delete(vararg event: Event): Int
    suspend fun insert(vararg event: Event): List<Long>
    suspend fun insert(event: Event): Long
    suspend fun update(event: Event): Int
}
