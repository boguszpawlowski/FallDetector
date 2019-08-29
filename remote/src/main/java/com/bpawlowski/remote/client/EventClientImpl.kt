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
import timber.log.Timber

internal class EventClientImpl(
	private val eventApi: EventApi,
	private val dispatcher: ContextProvider
) : EventClient {

	override suspend fun getEvents() = withContext(dispatcher.IO) {
		eventApi.getEvents().call().map { it.map { it.toDomain() } }
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