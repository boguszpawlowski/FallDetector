package com.bpawlowski.data.datasource

import com.bpawlowski.domain.model.Sensitivity
import com.bpawlowski.domain.model.ServiceState
import kotlinx.coroutines.flow.Flow

interface ServiceStateLocalDataSource {
    fun getIsRunningFlow(): Flow<Boolean>
    fun getSensitivityFlow(): Flow<Sensitivity>
    suspend fun update(serviceState: ServiceState): Int
    suspend fun get(): ServiceState?
    suspend fun initiateState(): Long
    suspend fun getIsRunning(): Boolean?
    suspend fun updateSensitivity(sensitivity: Sensitivity): Int
    suspend fun updateIsRunning(isRunning: Boolean): Int
}
