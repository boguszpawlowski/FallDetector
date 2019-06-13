package bogusz.com.service.location

import android.location.Location
import io.reactivex.Maybe

interface LocationProvider {
    suspend fun getLastKnownLocation(): Location
}