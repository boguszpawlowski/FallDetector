package com.bpawlowski.system.location

import android.location.Location
import com.bpawlowski.core.domain.Result

interface LocationProvider {
    suspend fun getLastKnownLocation(): Result<Location>
}