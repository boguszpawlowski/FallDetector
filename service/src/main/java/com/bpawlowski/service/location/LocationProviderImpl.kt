package com.bpawlowski.service.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.bpawlowski.service.database.FallDetectorResult
import com.bpawlowski.service.database.failure
import com.bpawlowski.service.database.success
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class LocationProviderImpl(
	context: Context
) : LocationProvider {

	private var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

	@SuppressLint("MissingPermission")
	override suspend fun getLastKnownLocation(): FallDetectorResult<Location> = suspendCoroutine { cont ->
		fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
			location?.let { cont.resume(success(it)) }
		}
		fusedLocationProviderClient.lastLocation.addOnFailureListener {
			cont.resume(failure(it))
		}
	}
}
