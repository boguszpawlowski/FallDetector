package com.example.bpawlowski.falldetector.screens.main.events

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bpawlowski.domain.model.Event
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.domain.toMarker
import com.example.bpawlowski.falldetector.screens.main.MainActivity
import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.util.setVisible
import com.example.bpawlowski.falldetector.util.snackbar
import com.example.bpawlowski.falldetector.util.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_event_details.buttonAttend
import kotlinx.android.synthetic.main.fragment_event_details.buttonBack
import kotlinx.android.synthetic.main.fragment_event_details.progressBar
import kotlinx.android.synthetic.main.fragment_event_details.scrollView
import kotlinx.android.synthetic.main.fragment_event_details.txtAttending
import kotlinx.android.synthetic.main.fragment_event_details.txtEventDate
import kotlinx.android.synthetic.main.fragment_event_details.txtEventTitle
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CAMERA_ZOOM_DISTANT = 18f

class EventDetailsFragment : BaseFragment<EventDetailsViewState>(), OnMapReadyCallback {

    override val layoutResID = R.layout.fragment_event_details

    override val viewModel: EventDetailsViewModel by viewModel()

    private val sharedViewModel: MainViewModel by sharedViewModel()

    override val shouldShowActionBar = false

    private val args: EventDetailsFragmentArgs by navArgs()

    lateinit var map: GoogleMap // todo create view for map, pass lifecycle events

    private val darkModeObserver: Observer<Boolean> by lazy {
        Observer<Boolean> { darkMode ->
            if (darkMode) {
                map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireContext(),
                        R.raw.map_night
                    )
                )
            }
        }
    }

    override fun invalidate(state: EventDetailsViewState) {
        state.eventData()?.let { event ->
            txtEventDate.text = event.toMarker().exactDate
            txtEventTitle.text = event.title
            txtAttending.text = getString(R.string.text_attending_count, event.attendingUsers)
            buttonAttend.setText(if (event.isAttending) R.string.btn_leave else R.string.btn_attend)
        }

        val loading = state.eventData is Loading || state.updateAttendingRequest is Loading

        progressBar.setVisible(loading)
        buttonAttend.isEnabled = loading.not()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.loadEvent(args.eventId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewScope.launch {
            viewModel.subscribe(EventDetailsViewState::updateAttendingRequest) {
                it.onFailure {
                    snackbar(message = it.rationale)
                }
            }
        }

        buttonAttend.setOnClickListener { viewModel.updateAttending() }

        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        (childFragmentManager.findFragmentById(R.id.eventMapFragment) as SafeScrollMapFragment).let {
            it.getMapAsync(this)
            it.scrollView = scrollView
        }
    }

    override fun onDestroyView() {
        (activity as? MainActivity)?.supportActionBar?.show()

        super.onDestroyView()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            uiSettings.isMapToolbarEnabled = true
        }

        viewScope.launch {
            viewModel.subscribeToAll(EventDetailsViewState::eventData) { result ->
                result.onSuccess { invalidateMapMarker(it) }
            }
        }

        sharedViewModel.darkModeLiveData.observe(viewLifecycleOwner, darkModeObserver)
    }

    private fun invalidateMapMarker(event: Event) {
        val marker = event.toMarker()
        val bitmap = R.drawable.ic_directions_run_black_24dp.toBitmap(requireContext())
        with(map) {
            clear()
            addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    .position(marker.latLng)
                    .title(event.title)
            )
            animateCamera(CameraUpdateFactory.newLatLngZoom(marker.latLng, CAMERA_ZOOM_DISTANT))
        }
    }
}
