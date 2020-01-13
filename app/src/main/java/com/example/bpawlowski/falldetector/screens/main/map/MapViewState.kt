package com.example.bpawlowski.falldetector.screens.main.map

import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.google.android.gms.maps.model.LatLng

data class MapViewState(
    val events: StateValue<List<EventClusterItem>> = StateValue.Uninitialized,
    val userLocation: StateValue<LatLng> = StateValue.Uninitialized
) : MviState
