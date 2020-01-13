package com.example.bpawlowski.falldetector.screens.main.map

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.util.layoutInflater
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.item_cluster_window.view.txtCreatorMobile
import kotlinx.android.synthetic.main.item_cluster_window.view.txtEventTitle

class EventInfoWindowAdapter(
    private val context: Context,
    private val markerMap: Map<LatLng, EventClusterItem>
) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?) = null

    @SuppressLint("InflateParams")
    override fun getInfoWindow(marker: Marker?): View? =
        context.layoutInflater.inflate(R.layout.item_cluster_window, null, true).apply {
            val event = markerMap[marker?.position]
            txtEventTitle.text = event?.eventTitle
            txtCreatorMobile.text = event?.creatorMobile
        }
}
