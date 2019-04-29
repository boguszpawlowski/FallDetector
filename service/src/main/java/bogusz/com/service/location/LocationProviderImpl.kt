package bogusz.com.service.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import io.reactivex.Maybe
import javax.inject.Inject

internal class LocationProviderImpl @Inject constructor(
    context: Context
) : LocationProvider {

    private var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): Maybe<Location> {
        return Maybe.create<Location> { emitter ->
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.let { location ->
                        emitter.onSuccess(location)
                    }
                } else {
                    emitter.onComplete()
                }
            }
            fusedLocationProviderClient.lastLocation.addOnFailureListener { exception ->
                emitter.onError(exception)
            }
        }
    }
}
