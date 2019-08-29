package com.example.bpawlowski.falldetector.screens.main.map

import androidx.lifecycle.LiveData
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.database.util.map
import com.bpawlowski.database.util.mapList
import com.bpawlowski.system.location.LocationProvider
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.EventMarker
import com.google.android.gms.maps.model.LatLng

class MapViewModel(
	private val eventRepository: EventRepository,
	private val locationProvider: LocationProvider
) : BaseViewModel() {

	val eventsData: LiveData<List<EventMarker>>
		get() = eventRepository.allEvents.mapList { EventMarker(LatLng(it.latLang.first, it.latLang.second), it.title) }

	val userLocationData: LiveData<LatLng>
		get() = locationProvider.locationLiveData.map { LatLng(it.latitude, it.longitude) }
}