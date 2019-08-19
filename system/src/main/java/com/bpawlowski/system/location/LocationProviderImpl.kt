package com.bpawlowski.system.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.bpawlowski.database.domain.FallDetectorResult
import com.bpawlowski.database.domain.failure
import com.bpawlowski.database.domain.success
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
