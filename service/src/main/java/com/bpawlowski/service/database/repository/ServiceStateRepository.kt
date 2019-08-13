package com.bpawlowski.service.database.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.service.database.FallDetectorResult
import com.bpawlowski.service.model.Sensitivity
import com.bpawlowski.service.model.ServiceState

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