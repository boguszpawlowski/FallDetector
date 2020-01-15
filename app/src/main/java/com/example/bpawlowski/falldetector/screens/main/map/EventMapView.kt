package com.example.bpawlowski.falldetector.screens.main.map

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.R.layout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.safe_scroll_map_view.view.mapView

typealias OnEventClickedListener = (Long) -> Unit

class EventsMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    init {
        inflate(context, layout.safe_scroll_map_view, this)

        mapView.getMapAsync(this)
    }

    var onEventInfoClickedListener: OnEventClickedListener? = null

    var isLocationEnabled: Boolean = false
        set(value) {
            map?.isMyLocationEnabled = value
            field = value
        }

    var userLocation: LatLng? = null
        set(value) {
            field = value
            moveToUsersLocation()
        }

    var nightMode: Boolean = false
        set(value) {
            field = value
            toggleNightMode()
        }

    private val markerMap = hashMapOf<LatLng, EventClusterItem>()

    private var infoWindowAdapter: EventInfoWindowAdapter? = null

    private var clusterManager: ClusterManager<EventClusterItem>? = null

    private var map: GoogleMap? = null

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap?.apply {
            setOnMarkerClickListener(this@EventsMapView)
            setOnInfoWindowClickListener(this@EventsMapView)
            uiSettings.isMapToolbarEnabled = true
            isMyLocationEnabled = isLocationEnabled
        }
        setupClusterManager()
        setupInfoWindowAdapter()
        drawMarkers()
        moveToUsersLocation()
        toggleNightMode()
    }

    override fun onMarkerClick(marker: Marker?) = marker?.let {
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, CAMERA_ZOOM_DEFAULT))
        marker.showInfoWindow()
        true
    } ?: false

    override fun onInfoWindowClick(marker: Marker) {
        markerMap[marker.position]?.eventId?.let { eventId ->
            onEventInfoClickedListener?.invoke(eventId)
        }
    }

    fun invalidateMapMarkers(items: List<EventClusterItem>) {
        items.forEach { item ->
            if (markerMap[item.position] == null) {
                markerMap[item.position] = item
            }
        }
        setupInfoWindowAdapter()
        drawMarkers()
    }

    fun onCreate(bundle: Bundle?) = mapView.onCreate(bundle)

    fun onStart() = mapView.onStart()

    fun onResume() = mapView.onResume()

    fun onPause() = mapView.onPause()

    fun onStop() = mapView.onStop()

    fun onDestroy() {
        map?.clear()
        mapView.onDestroy()
        clusterManager = null
        onEventInfoClickedListener = null
    }

    private fun moveToUsersLocation() = userLocation?.let { location ->
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, CAMERA_ZOOM_DEFAULT))
    }

    private fun toggleNightMode() {
        if (nightMode) {
            map?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(context, R.raw.map_night)
            )
        }
    }

    private fun drawMarkers() = markerMap.values.forEach {
        clusterManager?.addItem(it)
    }

    private fun setupClusterManager() {
        if (clusterManager == null) {
            clusterManager = ClusterManager(context, map)

            clusterManager?.let {
                it.renderer = EventClusterRenderer(map, it, context)
            }

            map?.setOnCameraIdleListener(clusterManager)
        }
    }

    private fun setupInfoWindowAdapter() {
        infoWindowAdapter = EventInfoWindowAdapter(context, markerMap)
        map?.setInfoWindowAdapter(infoWindowAdapter)
    }
}

