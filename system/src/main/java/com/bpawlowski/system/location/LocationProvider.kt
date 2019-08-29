package com.bpawlowski.system.location

import android.location.Location
import androidx.lifecycle.LiveData
import com.bpawlowski.core.domain.Result

interface LocationProvider {
	val locationLiveData: LiveData<Location>
    suspend fun getLastKnownLocation(): Result<Location>
}