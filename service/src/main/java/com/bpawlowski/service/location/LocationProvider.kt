package com.bpawlowski.service.location

import android.location.Location
import com.bpawlowski.service.database.FallDetectorResult

interface LocationProvider {
    suspend fun getLastKnownLocation(): FallDetectorResult<Location>
}