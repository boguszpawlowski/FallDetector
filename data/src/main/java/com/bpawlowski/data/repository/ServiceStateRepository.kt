package com.bpawlowski.data.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.model.Sensitivity
import com.bpawlowski.core.model.ServiceState

interface ServiceStateRepository {

	suspend fun initiateState()

	fun getIsRunningData(): LiveData<Boolean>

	fun getSensitivityData(): LiveData<Sensitivity>

	suspend fun getServiceState(): Result<ServiceState>

	suspend fun getIsRunningFlag(): Result<Boolean>

	suspend fun updateSensitivity(sensitivity: Sensitivity): Result<Unit>

	suspend fun updateIsRunning(isRunning: Boolean): Result<Unit>

	suspend fun updateState(serviceState: ServiceState): Result<Unit>
}