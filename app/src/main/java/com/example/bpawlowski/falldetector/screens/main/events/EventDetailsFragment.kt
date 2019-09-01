package com.example.bpawlowski.falldetector.screens.main.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentEventDetailsBinding
import com.example.bpawlowski.falldetector.domain.EventMarker
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.screens.main.MainActivity
import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.util.snackbar
import com.example.bpawlowski.falldetector.util.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_event_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val EVENT_ID_KEY = "event_id_key"
const val CAMERA_ZOOM_DISTANT = 18f

class EventDetailsFragment : BaseFragment<FragmentEventDetailsBinding>(), OnMapReadyCallback {

	override val layoutResID = R.layout.fragment_event_details

	override val viewModel: EventDetailsViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	lateinit var map: GoogleMap

	private val darkModeObserver: Observer<Boolean> by lazy {
		Observer<Boolean> { darkMode ->
			if (darkMode) {
				map.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_night))
			}
		}
	}

	private val screenStateObserver: Observer<ScreenState<Unit>> by lazy {
		Observer<ScreenState<Unit>> { state ->
			state.onFailure {
				snackbar(message = it.rationale)
			}
		}
	}

	private val eventMarkerObserver: Observer<EventMarker> by lazy {
		Observer<EventMarker> { event ->
			val bitmap = R.drawable.ic_directions_run_black_24dp.toBitmap(requireContext())
			map.addMarker(
				MarkerOptions()
					.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
					.position(event.latLng)
					.title(event.title)
			)
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(event.latLng, CAMERA_ZOOM_DISTANT))
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (savedInstanceState == null) {
			val eventId = arguments?.getLong(EVENT_ID_KEY) ?: throw UnsupportedOperationException()
			viewModel.loadEvent(eventId)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		(activity as? MainActivity)?.supportActionBar?.hide()

		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		collapsingToolbar.apply {
			setExpandedTitleColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.transparent
				)
			) //todo check if you can display home button?
		}

		(childFragmentManager.findFragmentById(R.id.eventMapFragment) as SafeScrollMapFragment).let {
			it.getMapAsync(this)
			it.scrollView = scrollView
		}
		viewModel.screenStateData.observe(viewLifecycleOwner, screenStateObserver)
	}

	override fun onDestroyView() {
		(activity as? MainActivity)?.supportActionBar?.show()

		super.onDestroyView()
	}

	override fun onMapReady(googleMap: GoogleMap) {
		map = googleMap.apply {
			uiSettings.isMapToolbarEnabled = true
		}

		viewModel.eventMarkerData.observe(viewLifecycleOwner, eventMarkerObserver)
		sharedViewModel.darkModeLiveData.observe(viewLifecycleOwner, darkModeObserver)
	}
}