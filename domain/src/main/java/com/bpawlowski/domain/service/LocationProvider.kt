package com.bpawlowski.domain.service

import com.bpawlowski.domain.Result
import com.bpawlowski.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    val locationFlow: Flow<Location>
    suspend fun getLastKnownLocation(): Result<Location>
}
