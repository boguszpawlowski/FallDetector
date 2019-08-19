package com.bpawlowski.database.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.database.domain.FallDetectorResult
import com.bpawlowski.database.entity.Sensitivity
import com.bpawlowski.database.entity.ServiceState

interface ServiceStateRepository {

	suspend fun initiateState()

	fun getIsRunningData(): LiveData<Boolean>

	fun getSensitivityData(): LiveData<Sensitivity>

	suspend fun getServiceState(): FallDetectorResult<ServiceState>

	suspend fun getIsRunningFlag(): FallDetectorResult<Boolean>

	suspend fun updateSensitivity(sensitivity: Sensitivity): FallDetectorResult<Unit>

	suspend fun updateIsRunning(isRunning: Boolean): FallDetectorResult<Unit>

	suspend fun updateState(serviceState: ServiceState): FallDetectorResult<Unit>
}