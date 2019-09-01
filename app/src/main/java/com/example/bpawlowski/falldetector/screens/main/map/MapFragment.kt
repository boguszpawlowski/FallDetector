package com.example.bpawlowski.falldetector.screens.main.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentMapBinding
import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.util.checkPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CAMERA_ZOOM_DEFAULT = 15f

class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

	override val layoutResID = R.layout.fragment_map

	override val viewModel: MapViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	private var clusterManager: ClusterManager<EventClusterItem>? = null

	private lateinit var map: GoogleMap

	private val markerMap = hashMapOf<LatLng, EventClusterItem>()

	private val onInfoClicked = GoogleMap.OnInfoWindowClickListener { marker ->
		markerMap[marker.position]?.eventId?.let { eventId ->
			findNavController().navigate(
				MapFragmentDirections.showEventDetails(eventId)
			)
		}
	}

	private val eventsObserver: Observer<List<EventClusterItem>> by lazy {
		Observer<List<EventClusterItem>> { items ->
			items.forEach { item ->
				if (markerMap[item.position] == null) {
					markerMap[item.position] = item
					clusterManager?.addItem(item)
				}
			}
		}
	}

	private val darkModeObserver: Observer<Boolean> by lazy {
		Observer<Boolean> { darkMode ->
			if (darkMode) {
				map.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_night))
			}
		}
	}

	private val userLocationObserver: Observer<LatLng> by lazy {
		Observer<LatLng> { userLocation ->
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, CAMERA_ZOOM_DEFAULT))
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		if (savedInstanceState == null) {
			viewModel.loadData()
		}
		(childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment).getMapAsync(this)
	}

	override fun onMapReady(googleMap: GoogleMap) {
		map = googleMap.apply {
			setOnMarkerClickListener(this@MapFragment)
			setOnInfoWindowClickListener(onInfoClicked)
			uiSettings.isMapToolbarEnabled = true
		}
		setupClusterManager()

		with(viewModel) {
			eventsData.observe(viewLifecycleOwner, eventsObserver)
			checkPermission(
				activity = requireActivity(),
				permission = Manifest.permission.ACCESS_FINE_LOCATION,
				onGranted = {
					map.isMyLocationEnabled = true
					viewModel.userLocationData.observe(viewLifecycleOwner, userLocationObserver)
				})
		}

		sharedViewModel.darkModeLiveData.observe(viewLifecycleOwner, darkModeObserver)
	}

	override fun onMarkerClick(marker: Marker?) = marker?.let {
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, CAMERA_ZOOM_DEFAULT))
		marker.showInfoWindow()
		true
	} ?: false

	private fun setupClusterManager() {
		if (clusterManager == null) {
			clusterManager = ClusterManager(requireContext(), map)

			map.setOnCameraIdleListener(clusterManager)
		}
	}
}
