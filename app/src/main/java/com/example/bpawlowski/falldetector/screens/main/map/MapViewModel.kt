package com.example.bpawlowski.falldetector.screens.main.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.core.domain.EventWrapper
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.database.util.map
import com.bpawlowski.database.util.mapList
import com.bpawlowski.system.location.LocationProvider
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.EventMarker
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlin.random.Random

class MapViewModel(
	private val eventRepository: EventRepository,
	private val locationProvider: LocationProvider
) : BaseViewModel() {

	val eventsData: LiveData<List<EventMarker>> //todo change this
		get() = eventRepository.allEvents.mapList {  EventMarker(LatLng(51.1078852 + Random.nextDouble(-0.06,0.06), 17.0385376 + Random.nextDouble(-0.06,0.06)), it.title, it.id) }

	val userLocationData: LiveData<EventWrapper<LatLng>> //todo change this to getting from livedata
		get() = locationProvider.locationLiveData.map { EventWrapper(LatLng(it.latitude, it.longitude)) }

	fun loadData() = viewModelScope.launch {
		eventRepository.syncEvents()
	}
}