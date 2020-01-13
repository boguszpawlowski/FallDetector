@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.bpawlowski.system.location

import android.annotation.SuppressLint
import android.content.Context
import com.bpawlowski.domain.Result
import com.bpawlowski.domain.failure
import com.bpawlowski.domain.model.Location
import com.bpawlowski.domain.service.LocationProvider
import com.bpawlowski.domain.success
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class LocationProviderImpl(
    context: Context
) : LocationProvider {

    private var fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    override val locationFlow: Flow<Location> by lazy {
        getLocationFlow(fusedLocationProviderClient)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): Result<Location> = suspendCoroutine { cont ->
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let { cont.resume(success(Location(it.latitude, it.longitude))) }
        }
        fusedLocationProviderClient.lastLocation.addOnFailureListener {
            cont.resume(failure(it))
        }
    }
}
