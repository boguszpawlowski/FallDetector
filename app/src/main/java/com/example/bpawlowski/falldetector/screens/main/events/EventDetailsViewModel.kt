package com.example.bpawlowski.falldetector.screens.main.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.core.model.Event
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.database.util.map
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.EventMarker
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.domain.toMarker
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.launch

class EventDetailsViewModel(
	private val eventRepository: EventRepository
) : BaseViewModel() {

	val eventData = MutableLiveData<Event>()
	val eventMarkerData: LiveData<EventMarker> = eventData.map { it.toMarker() }

	private val _screenStateData = MutableLiveData<ScreenState<Unit>>()
	val screenStateData = _screenStateData.toSingleEvent()

	fun loadEvent(eventId: Long) = viewModelScope.launch {
		eventRepository.getEvent(eventId).onSuccess {
			eventData.postValue(it)
		}.onFailure {
			_screenStateData.postValue(ScreenState.Failure(it))
		}
	}
}