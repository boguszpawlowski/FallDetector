package com.example.bpawlowski.falldetector.domain

import com.bpawlowski.core.model.Event
import com.bpawlowski.core.util.dayMonthDate
import com.bpawlowski.core.util.dayMonthHourDate
import com.google.android.gms.maps.model.LatLng

data class EventMarker(
	val latLng: LatLng,
	val title: String,
	val eventId: Long?,
	val date: String,
	val exactDate: String,
	val attendingUsers: String
)

fun Event.toMarker() = EventMarker(
	LatLng(latLang.first, latLang.second),
	title,
	id,
	date.dayMonthDate(),
	date.dayMonthHourDate(),
	attendingUsers.toString()
)