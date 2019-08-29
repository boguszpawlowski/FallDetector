package com.bpawlowski.system.location

import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

internal class LocationLiveData(private val fusedLocationClient: FusedLocationProviderClient): LiveData<Location>(){

	private val locationCallback = object : LocationCallback() {
		override fun onLocationResult(locationResult: LocationResult?) {
			locationResult?.lastLocation?.let {
				postValue(it)
			}
		}
	}

	override fun onActive() {
		super.onActive()

		fusedLocationClient.requestLocationUpdates(
			LocationRequest(),
			locationCallback,
			null
		)
	}

	override fun onInactive() {
		super.onInactive()

		fusedLocationClient.removeLocationUpdates(locationCallback)
	}
}