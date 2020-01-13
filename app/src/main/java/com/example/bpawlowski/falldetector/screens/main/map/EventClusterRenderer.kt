package com.example.bpawlowski.falldetector.screens.main.map

import android.content.Context
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.util.toBitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class EventClusterRenderer(
    map: GoogleMap?,
    clusterManager: ClusterManager<EventClusterItem>,
    private val context: Context
) : DefaultClusterRenderer<EventClusterItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: EventClusterItem?, markerOptions: MarkerOptions?) {
        val bitmap = R.drawable.ic_person_pin_black_32dp.toBitmap(context)
        val markerDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
        markerOptions?.icon(markerDescriptor)
    }
}
