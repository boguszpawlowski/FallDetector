package com.example.bpawlowski.falldetector.screens.main.map

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ItemClusterWindowBinding
import com.example.bpawlowski.falldetector.util.layoutInflater
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class EventInfoWindowAdapter(
	private val context: Context,
	private val markerMap: Map<LatLng, EventClusterItem>
) : GoogleMap.InfoWindowAdapter {

	override fun getInfoContents(p0: Marker?) = null

	@SuppressLint("InflateParams")
	override fun getInfoWindow(marker: Marker?): View? {
		val binding = DataBindingUtil.inflate<ItemClusterWindowBinding>(context.layoutInflater, R.layout.item_cluster_window, null, true)
		val event = markerMap[marker?.position]
		binding.txtEventTitle.text = event?.eventTitle
		binding.txtCreatorMobile.text = event?.creatorMobile
		return binding?.root
	}
}