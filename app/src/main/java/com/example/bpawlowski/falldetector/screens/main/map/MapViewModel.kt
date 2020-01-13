package com.example.bpawlowski.falldetector.screens.main.map

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.repository.EventRepository
import com.bpawlowski.domain.service.LocationProvider
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.loading
import com.example.bpawlowski.falldetector.domain.success
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@Suppress("EXPERIMENTAL_API_USAGE")
class MapViewModel(
    initialState: MapViewState = MapViewState(),
    private val eventRepository: EventRepository,
    private val locationProvider: LocationProvider
) : BaseViewModel<MapViewState>(initialState) {

    init {
        loadData()
        loadUserLocation()
    }

    fun loadUserLocation() {
        viewModelScope.launch {
            locationProvider.getLastKnownLocation().onSuccess {
                setState {
                    copy(userLocation = success(LatLng(it.lat, it.lng)))
                }
            }
        }
    }

    fun loadData() = viewModelScope.launch {
        eventRepository.syncEvents()
        eventRepository.getEvents()
            .onStart {
                setState { copy(events = loading()) }
            }
            .map { it.map { it.toClusterItem() } }
            .collect {
                setState { copy(events = success(it)) }
            }
    }
}
