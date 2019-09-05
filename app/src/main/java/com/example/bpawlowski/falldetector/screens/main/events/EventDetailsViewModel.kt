package com.example.bpawlowski.falldetector.screens.main.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.core.model.Event
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.database.util.map
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.EventMarker
import com.example.bpawlowski.falldetector.domain.ScreenResult
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.domain.reduce
import com.example.bpawlowski.falldetector.domain.toMarker
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class EventDetailsViewModel(
	private val eventRepository: EventRepository
) : BaseViewModel() {

	val _eventData = MediatorLiveData<Event>()
	val eventMarkerData: LiveData<EventMarker> = _eventData.map { it.toMarker() }

	private val _screenStateData = MutableLiveData<ScreenState<Unit>>()
	val screenStateData = _screenStateData.toSingleEvent()

	fun loadEvent(eventId: Long) = viewModelScope.launch {
		eventRepository.getEvent(eventId).onSuccess {
			_eventData.postValue(it)
		}.onFailure {
			_screenStateData.reduce(ScreenResult.Failure(it))
		}
	}

	fun updateAttending() = viewModelScope.launch {
		_screenStateData.reduce(ScreenResult.Loading)

		eventRepository.updateAttending(_eventData.value).onSuccess { updatedEvent ->
			_eventData.postValue(updatedEvent)
			_screenStateData.reduce(ScreenResult.Loaded)
		}.onFailure {
			_screenStateData.reduce(ScreenResult.Failure(it))
		}.onException {
			Timber.e(it)
		}
	}
}