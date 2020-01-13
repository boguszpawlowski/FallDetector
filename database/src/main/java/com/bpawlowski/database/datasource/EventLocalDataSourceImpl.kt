package com.bpawlowski.database.datasource

import com.bpawlowski.domain.model.Event
import com.bpawlowski.data.datasource.EventLocalDataSource
import com.bpawlowski.database.dao.EventDao
import com.bpawlowski.database.entity.toDomain
import com.bpawlowski.database.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventLocalDataSourceImpl(
    private val eventDao: EventDao
) : EventLocalDataSource {

    override fun getAllFlow(): Flow<List<Event>> =
        eventDao.getAllFlow().map { it.map { it.toDomain() } }

    override suspend fun getAll(): List<Event> =
        eventDao.getAll().map { it.toDomain() }

    override suspend fun getById(eventId: Long): Event? =
        eventDao.getEventById(eventId)?.toDomain()

    override suspend fun delete(vararg event: Event): Int =
        eventDao.delete(*event.map { it.toEntity() }.toTypedArray())

    override suspend fun insert(vararg event: Event): List<Long> =
        eventDao.insert(*event.map { it.toEntity() }.toTypedArray())

    override suspend fun insert(event: Event): Long =
        eventDao.insert(event.toEntity())

    override suspend fun update(event: Event): Int =
        eventDao.update(event.toEntity())
}
