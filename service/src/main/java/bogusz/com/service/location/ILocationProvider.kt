package bogusz.com.service.location

import android.location.Location
import io.reactivex.Maybe

interface ILocationProvider {
    fun getLastKnownLocation(): Maybe<Location>
}