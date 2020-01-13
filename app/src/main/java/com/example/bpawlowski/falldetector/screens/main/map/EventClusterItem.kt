package com.example.bpawlowski.falldetector.screens.main.map

import com.bpawlowski.domain.model.Event
import com.bpawlowski.domain.util.dayMonthDate
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class EventClusterItem(
    val eventId: Long?,
    val eventTitle: String,
    val creatorMobile: String,
    private val eventLocation: LatLng,
    private val eventSnippet: String?
) : ClusterItem {

    override fun getSnippet() = eventSnippet

    override fun getTitle() = eventTitle

    override fun getPosition() = eventLocation
}

fun Event.toClusterItem() = EventClusterItem(
    eventId = id,
    eventTitle = title,
    creatorMobile = creatorMobile,
    eventLocation = LatLng(latLang.first, latLang.second),
    eventSnippet = date.dayMonthDate()
)
