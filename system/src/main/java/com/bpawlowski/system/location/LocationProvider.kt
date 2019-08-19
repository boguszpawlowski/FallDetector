package com.bpawlowski.system.location

import android.location.Location
import com.bpawlowski.database.domain.FallDetectorResult

interface LocationProvider {
    suspend fun getLastKnownLocation(): FallDetectorResult<Location>
}