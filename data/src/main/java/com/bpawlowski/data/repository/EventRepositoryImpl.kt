package com.bpawlowski.data.repository

import com.bpawlowski.data.BaseRepository
import com.bpawlowski.data.datasource.EventLocalDataSource
import com.bpawlowski.data.datasource.EventRemoteDataSource
import com.bpawlowski.domain.Result
import com.bpawlowski.domain.exception.FallDetectorException
import com.bpawlowski.domain.failure
import com.bpawlowski.domain.model.Event
import com.bpawlowski.domain.repository.EventRepository
import com.bpawlowski.domain.success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class EventRepositoryImpl(
    private val eventRemoteDataSource: EventRemoteDataSource,
    private val eventLocalDataSource: EventLocalDataSource
) : BaseRepository(), EventRepository {

    /**
     * Cache methods
     */
    override fun getEvents(): Flow<List<Event>> =
        eventLocalDataSource.getAllFlow()

    override suspend fun getEvent(eventId: Long): Result<Event> {
        val event = eventLocalDataSource.getById(eventId)
        return if (event != null) {
            success(event)
        } else {
            failure(FallDetectorException.NoSuchRecordException(eventId))
        }
    }

    override suspend fun updateAttending(event: Event?): Result<Event> {
        event ?: return failure(IllegalArgumentException("Event cant be null"))
        val updatedAttending =
            if (event.isAttending) event.attendingUsers - 1 else event.attendingUsers + 1
        val updatedEvent =
            event.copy(attendingUsers = updatedAttending, isAttending = event.isAttending.not())

        return updateEvent(updatedEvent).flatMap {
            eventRemoteDataSource.putEvent(event)
        }.map { updatedEvent }
    }

    override suspend fun syncEvents() = withContext(backgroundContext) {
        eventRemoteDataSource.getEvents().onSuccess { remoteEvents ->
            val localEvents = eventLocalDataSource.getAll().toMutableList()
            val eventsToRemove = localEvents
                .filter { localEvent -> remoteEvents.all { localEvent.remoteId != it.remoteId } }
                .onEach { localEvents.remove(it) }

            val newEvents = remoteEvents
                .filter { remoteEvent -> localEvents.all { remoteEvent.remoteId != it.remoteId } }

            eventLocalDataSource.delete(*eventsToRemove.toTypedArray())
            eventLocalDataSource.insert(*newEvents.toTypedArray())

            remoteEvents.forEach { remoteEvent ->
                localEvents.firstOrNull { remoteEvent.remoteId == it.remoteId && it != remoteEvent }
                    ?.let {
                        eventLocalDataSource.update(
                            remoteEvent.copy(
                                id = it.id,
                                isAttending = it.isAttending
                            )
                        )
                    }
            }
        }
        Unit
    }

    override suspend fun addEvent(event: Event): Result<Unit> = withContext(iOContext) {
        eventRemoteDataSource.postEvent(event).flatMap {
            val id = eventLocalDataSource.insert(event.copy(remoteId = it))
            if (id != -1L) {
                success(Unit)
            } else {
                failure(FallDetectorException.NoSuchRecordException(id))
            }
        }
    }

    override suspend fun updateEvent(event: Event): Result<Unit> = withContext(backgroundContext) {
        eventRemoteDataSource.putEvent(event).flatMap {
            val updatedColumns = eventLocalDataSource.update(event)
            if (updatedColumns > 0) {
                success(Unit)
            } else {
                failure(FallDetectorException.RecordNotUpdatedException(event.id))
            }
        }
    }

    override suspend fun removeEvent(event: Event): Result<Unit> = withContext(backgroundContext) {
        eventRemoteDataSource.deleteEvent(event).flatMap {
            val deletedColumns = eventLocalDataSource.delete(event)
            if (deletedColumns > 0) {
                success(Unit)
            } else {
                failure(FallDetectorException.RecordNotDeletedException(event.id))
            }
        }
    }
}
