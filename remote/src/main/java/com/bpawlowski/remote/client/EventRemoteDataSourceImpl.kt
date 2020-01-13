package com.bpawlowski.remote.client

import com.bpawlowski.data.datasource.EventRemoteDataSource
import com.bpawlowski.domain.Result
import com.bpawlowski.domain.failure
import com.bpawlowski.domain.model.Event
import com.bpawlowski.remote.api.EventApi
import com.bpawlowski.remote.model.toDomain
import com.bpawlowski.remote.model.toDto
import com.bpawlowski.remote.util.call
import kotlin.random.Random

internal class EventRemoteDataSourceImpl(
    private val eventApi: EventApi
) : EventRemoteDataSource {

    // TODO change this fun
    override suspend fun getEvents() = call(eventApi.getEvents()).map {
        it.map {
            it.toDomain().copy(
                latLang = 51.1078852 + Random.nextDouble(
                    -0.06,
                    0.06
                ) to 17.0385376 + Random.nextDouble(-0.06, 0.06)
            )
        }
    }

    override suspend fun putEvent(event: Event): Result<Unit> {
        val id = event.remoteId ?: return failure(Exception("Remote id cannot be null"))
        return call(eventApi.putEvent(id, event.toDto()))
    }

    override suspend fun deleteEvent(event: Event): Result<Unit> {
        val id = event.remoteId ?: return failure(Exception("Remote id cannot be null"))
        return call(eventApi.deleteEvent(id))
    }

    override suspend fun postEvent(event: Event): Result<Long> =
        call(eventApi.postEvent(event.toDto())).map { it.id ?: -1L }
}
