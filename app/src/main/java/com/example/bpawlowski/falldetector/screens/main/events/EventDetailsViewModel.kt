package com.example.bpawlowski.falldetector.screens.main.events

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.repository.EventRepository
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val eventRepository: EventRepository,
    initialState: EventDetailsViewState = EventDetailsViewState()
) : BaseViewModel<EventDetailsViewState>(initialState) {

    fun loadEvent(eventId: Long) = viewModelScope.launch {
        setState { copy(eventData = Loading) }
        eventRepository.getEvent(eventId).onSuccess {
            setState { copy(eventData = success(it)) }
        }.onFailure {
            setState { copy(eventData = failure(it)) }
        }
    }

    fun updateAttending() = viewModelScope.launch {
        setState { copy(updateAttendingRequest = Loading) }
        eventRepository.updateAttending(currentState.eventData()).onSuccess { updatedEvent ->
            setState { copy(eventData = success(updatedEvent), updateAttendingRequest = success(Unit)) }
        }.onFailure {
            setState { copy(updateAttendingRequest = failure(it)) }
        }
    }
}
