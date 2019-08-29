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
import com.example.bpawlowski.falldetector.util.toSingleEvent
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlin.random.Random

class EventDetailsViewModel(
	private val eventRepository: EventRepository
) : BaseViewModel() {

	val eventData = MutableLiveData<Event>()
	val eventMarkerData: LiveData<EventMarker>
		get() = eventData.map { EventMarker(LatLng(51.1078852 + Random.nextDouble(-0.06,0.06), 17.0385376 + Random.nextDouble(-0.06,0.06)), it.title, it.id) }

	private val _screenStateData = MutableLiveData<ScreenState<Unit>>()
	val screenStateData = _screenStateData.toSingleEvent()

	//todo change this - do not invoke fun to load event (pass parameters or smthing)
	fun loadEvent(eventId: Long) = viewModelScope.launch {
		eventRepository.getEvent(eventId).onSuccess {
			eventData.postValue(it)
		}.onFailure {
			_screenStateData.postValue(ScreenState.Failure(it))
		}
	}
}