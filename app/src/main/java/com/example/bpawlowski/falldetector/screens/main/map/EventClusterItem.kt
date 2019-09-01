package com.example.bpawlowski.falldetector.screens.main.map

import com.bpawlowski.core.model.Event
import com.bpawlowski.core.util.dayMonthDate
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class EventClusterItem(
	val eventId: Long?,
	private val eventLocation: LatLng,
	private val eventTitle: String,
	private val eventSnippet: String?
) : ClusterItem {

	override fun getSnippet() = eventSnippet

	override fun getTitle() = eventTitle

	override fun getPosition() = eventLocation
}

fun Event.toClusterItem() = EventClusterItem(
	id,
	LatLng(latLang.first, latLang.second),
	title,
	date.dayMonthDate()
)