package bogusz.com.service.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class LocationProviderImpl @Inject constructor(
    context: Context
) : LocationProvider {

    private var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): Location = suspendCoroutine { cont ->
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let { cont.resumeWith(Result.success(it)) }
        }
        fusedLocationProviderClient.lastLocation.addOnFailureListener {
            cont.resumeWithException(it)
        }
    }
}
