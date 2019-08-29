package com.example.bpawlowski.falldetector.domain

import com.google.android.gms.maps.model.LatLng

data class EventMarker(
	val latLng: LatLng,
	val title: String,
	val eventId: Long?
)