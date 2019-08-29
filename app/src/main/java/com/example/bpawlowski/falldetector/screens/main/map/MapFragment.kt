package com.example.bpawlowski.falldetector.screens.main.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

	private lateinit var map: GoogleMap

	override val layoutResID = R.layout.fragment_map

	override val viewModel: MapViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	private val eventsObserver: Observer<List<EventMarker>> by lazy {
		Observer<List<EventMarker>> { events ->
			val bitmap = R.drawable.ic_directions_run_black_24dp.toBitmap(requireContext())
			events.forEach { event ->
				map.addMarker(
					MarkerOptions()
						.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
						.position(event.latLng)
						.title(event.title)
				)
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		(childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment).getMapAsync(this)
	}

	override fun onMapReady(googleMap: GoogleMap) {
		map = googleMap
		map.setOnMarkerClickListener(this)

		with(viewModel) {
			eventsData.observe(viewLifecycleOwner, eventsObserver)
			checkPermission(
				activity = requireActivity(),
				permission = Manifest.permission.ACCESS_FINE_LOCATION,
				onGranted = {
					map.isMyLocationEnabled = true
				})
		}
	}

	override fun onMarkerClick(marker: Marker?) = marker?.let {
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, 15f))
		true
	} ?: false
}
