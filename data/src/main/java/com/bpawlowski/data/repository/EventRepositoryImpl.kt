package com.bpawlowski.data.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.domain.failure
import com.bpawlowski.core.domain.success
import com.bpawlowski.core.exception.FallDetectorException
import com.bpawlowski.core.model.Event
import com.bpawlowski.data.util.toDomain
import com.bpawlowski.data.util.toEntity
import com.bpawlowski.database.dao.EventDao
import com.bpawlowski.database.util.mapList
import com.bpawlowski.remote.client.EventClient

internal class EventRepositoryImpl(
    private val eventClient: EventClient,
    private val eventDao: EventDao
): EventRepository{

    /**
     * Cache methods
     */
    override val allEvents: LiveData<List<Event>>
        get() = eventDao.getAllData().mapList { it.toDomain() }

    override suspend fun getEvent(eventId: Long): Result<Event> {
        val event = eventDao.getEventById(eventId)?.toDomain()
        return if(event != null){
            success(event)
        } else {
            failure(FallDetectorException.NoSuchRecordException(eventId))
        }
    }

    /**
     * Remote methods
     */
    override suspend fun syncEvents() {
        val events = eventClient.getEvents()
        val localEvents = eventDao.getAll()

        val newEvents = events
            .filter { remoteEvent -> localEvents.all { remoteEvent.remoteId != it.remoteId } }
            .map { it.toEntity() }

        eventDao.insert(*newEvents.toTypedArray())
    }

    override suspend fun addEvent(event: Event): Result<Unit> =
        eventClient.postEvent(event).flatMap {
            val id = eventDao.insert(event.toEntity().copy(remoteId = it))
            return if(id != -1L){
                success(Unit)
            } else {
                failure(FallDetectorException.NoSuchRecordException(id))
            }
        }

    override suspend fun updateEvent(event: Event): Result<Unit> =
        eventClient.putEvent(event).flatMap {
            val updatedColumns = eventDao.update(event.toEntity())
            return if(updatedColumns > 0){
                success(Unit)
            } else {
                failure(FallDetectorException.RecordNotUpdatedException(event.id))
            }
        }

    override suspend fun removeEvent(event: Event): Result<Unit> =
        eventClient.deleteEvent(event).flatMap {
            val deletedColumns = eventDao.delete(event.toEntity())
            return if(deletedColumns > 0){
                success(Unit)
            } else {
                failure(FallDetectorException.RecordNotDeletedException(event.id))
            }
        }
}