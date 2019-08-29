package com.example.bpawlowski.falldetector.screens.main.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bpawlowski.system.model.AppSettings
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentMapBinding
import com.example.bpawlowski.falldetector.domain.EventMarker
import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CAMERA_ZOOM_DEFAULT = 15f

class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

	private lateinit var map: GoogleMap

	override val layoutResID = R.layout.fragment_map

	override val viewModel: MapViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	private val onInfoClicked = GoogleMap.OnInfoWindowClickListener { marker ->
		(marker.tag as? Long)?.let {
			findNavController().navigate(
				MapFragmentDirections.showEventDetails(it)
			)
		}
	}

	private val eventsObserver: Observer<List<EventMarker>> by lazy {
		Observer<List<EventMarker>> { events ->
			val bitmap = R.drawable.ic_directions_run_black_24dp.toBitmap(requireContext())
			events.forEach { event ->
				map.addMarker(
					MarkerOptions()
						.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
						.position(event.latLng)
						.title(event.title)
				).apply {
					tag = event.eventId
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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.loadData()
		(childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment).getMapAsync(this)
	}

	override fun onMapReady(googleMap: GoogleMap) {
		map = googleMap.apply {
			setOnMarkerClickListener(this@MapFragment)
			setOnInfoWindowClickListener(onInfoClicked)
			uiSettings.isMapToolbarEnabled = true
		}

		with(viewModel) {
			eventsData.observe(viewLifecycleOwner, eventsObserver)
			checkPermission(
				activity = requireActivity(),
				permission = Manifest.permission.ACCESS_FINE_LOCATION,
				onGranted = {
					map.isMyLocationEnabled = true
					zoomToMyLocation()
				})
		}

		sharedViewModel.darkModeLiveData.observe(viewLifecycleOwner, darkModeObserver)
	}

	override fun onMarkerClick(marker: Marker?) = marker?.let {
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, CAMERA_ZOOM_DEFAULT))
		marker.showInfoWindow()
		true
	} ?: false

	private fun zoomToMyLocation() { //todo observer to field
		viewModel.userLocationData.observe(viewLifecycleOwner, Observer {
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.value, 15f))
		})
	}
}
