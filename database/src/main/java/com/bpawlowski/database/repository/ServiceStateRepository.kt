package com.bpawlowski.database.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.core.model.Sensitivity
import com.bpawlowski.database.entity.ServiceStateDb

interface ServiceStateRepository {

	suspend fun initiateState()

	fun getIsRunningData(): LiveData<Boolean>

	fun getSensitivityData(): LiveData<Sensitivity>

	suspend fun getServiceState(): com.bpawlowski.core.domain.FallDetectorResult<ServiceStateDb>

	suspend fun getIsRunningFlag(): com.bpawlowski.core.domain.FallDetectorResult<Boolean>

	suspend fun updateSensitivity(sensitivity: Sensitivity): com.bpawlowski.core.domain.FallDetectorResult<Unit>

	suspend fun updateIsRunning(isRunning: Boolean): com.bpawlowski.core.domain.FallDetectorResult<Unit>

	suspend fun updateState(serviceState: ServiceStateDb): com.bpawlowski.core.domain.FallDetectorResult<Unit>
}