package com.bpawlowski.domain.repository

import com.bpawlowski.domain.Result
import com.bpawlowski.domain.model.Sensitivity
import com.bpawlowski.domain.model.ServiceState
import kotlinx.coroutines.flow.Flow

interface ServiceStateRepository {
    suspend fun initiateState()
    fun isRunning(): Flow<Boolean>
    fun getSensitivityFlow(): Flow<Sensitivity>
    suspend fun getServiceState(): Result<ServiceState>
    suspend fun getIsRunningFlag(): Result<Boolean>
    suspend fun updateSensitivity(sensitivity: Sensitivity): Result<Unit>
    suspend fun updateIsRunning(isRunning: Boolean): Result<Unit>
    suspend fun updateState(serviceState: ServiceState): Result<Unit>
}
