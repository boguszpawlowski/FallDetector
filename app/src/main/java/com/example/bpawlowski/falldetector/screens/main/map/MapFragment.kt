package com.example.bpawlowski.falldetector.screens.main.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bpawlowski.domain.util.doNothing
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.util.checkPermission
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CAMERA_ZOOM_DEFAULT = 15f

class MapFragment : BaseFragment<MapViewState>() {

    override val layoutResID = R.layout.fragment_map

    override val viewModel: MapViewModel by viewModel()

    private val sharedViewModel: MainViewModel by sharedViewModel()

    private val darkModeObserver: Observer<Boolean> by lazy {
        Observer<Boolean> { nightMode ->
            eventsMapView.nightMode = nightMode
        }
    }

    override fun invalidate(state: MapViewState) = doNothing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsMapView.onCreate(savedInstanceState)

        eventsMapView.onEventInfoClickedListener = { showEventDetails(it) }

        checkPermission(
            activity = requireActivity(),
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            onGranted = { eventsMapView.isLocationEnabled = true }
        )

        viewScope.launch {
            viewModel.subscribeToAll(MapViewState::events) { result ->
                result.onSuccess { eventsMapView.invalidateMapMarkers(it) }
            }
        }

        viewScope.launch {
            viewModel.subscribeOnce(MapViewState::userLocation) { locationResult ->
                locationResult.onSuccess { eventsMapView.userLocation = it }
            }
        }

        sharedViewModel.darkModeLiveData.observe(viewLifecycleOwner, darkModeObserver)
    }

    override fun onStart() {
        super.onStart()
        eventsMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        eventsMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        eventsMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        eventsMapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventsMapView.onDestroy()
    }

    private fun showEventDetails(eventId: Long) {
        findNavController().navigate(
            MapFragmentDirections.showEventDetails(eventId)
        )
    }
}
