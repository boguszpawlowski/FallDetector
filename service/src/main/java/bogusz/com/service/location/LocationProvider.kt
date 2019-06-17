package bogusz.com.service.location

import android.location.Location
import bogusz.com.service.database.FallDetectorResult

interface LocationProvider {
    suspend fun getLastKnownLocation(): FallDetectorResult<Location>
}