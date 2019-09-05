package com.bpawlowski.remote.client

import com.bpawlowski.core.coroutines.ContextProvider
import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.domain.failure
import com.bpawlowski.core.model.Event
import com.bpawlowski.remote.api.EventApi
import com.bpawlowski.remote.util.call
import com.bpawlowski.remote.util.toDomain
import com.bpawlowski.remote.util.toDto
import kotlinx.coroutines.withContext
import kotlin.random.Random

internal class EventClientImpl(
	private val eventApi: EventApi,
	private val dispatcher: ContextProvider
) : EventClient {

	override suspend fun getEvents() = withContext(dispatcher.IO) {
		eventApi.getEvents().call().map {
			it.map {
				it.toDomain().copy( //todo change this
					latLang = 51.1078852 + Random.nextDouble(-0.06, 0.06) to 17.0385376 + Random.nextDouble(-0.06, 0.06)
				)
			}
		}
	}

	override suspend fun putEvent(event: Event): Result<Unit> = withContext(dispatcher.IO) {
		val id = event.remoteId ?: return@withContext failure(Exception("Remote id cannot be null"))
		eventApi.putEvent(id, event.toDto()).call()
	}

	override suspend fun deleteEvent(event: Event): Result<Unit> = withContext(dispatcher.IO) {
		val id = event.remoteId ?: return@withContext failure(Exception("Remote id cannot be null"))
		eventApi.deleteEvent(id).call()
	}

	override suspend fun postEvent(event: Event): Result<Long> = withContext(dispatcher.IO) {
		eventApi.postEvent(event.toDto()).call().map { it.id ?: -1L }
	}
}