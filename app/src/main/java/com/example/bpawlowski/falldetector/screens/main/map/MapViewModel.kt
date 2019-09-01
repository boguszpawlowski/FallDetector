package com.example.bpawlowski.falldetector.screens.main.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.database.util.mapList
import com.bpawlowski.system.location.LocationProvider
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.toSingleEvent
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapViewModel(
	private val eventRepository: EventRepository,
	private val locationProvider: LocationProvider
) : BaseViewModel() {

	val eventsData = eventRepository.allEvents.mapList { it.toClusterItem() }

	private val _userLocationData = MutableLiveData<LatLng>().apply {
		viewModelScope.launch {
			locationProvider.getLastKnownLocation().onSuccess {
				postValue(LatLng(it.latitude, it.longitude))
			}
		}
	}
	val userLocationData: LiveData<LatLng> = _userLocationData.toSingleEvent()

	fun loadData() = viewModelScope.launch {
		eventRepository.syncEvents()
	}
}