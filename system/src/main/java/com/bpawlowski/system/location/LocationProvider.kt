package com.bpawlowski.system.location

import android.location.Location
import com.bpawlowski.core.domain.FallDetectorResult

interface LocationProvider {
    suspend fun getLastKnownLocation(): com.bpawlowski.core.domain.FallDetectorResult<Location>
}