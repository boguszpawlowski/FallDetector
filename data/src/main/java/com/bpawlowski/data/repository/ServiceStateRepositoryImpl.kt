package com.bpawlowski.data.repository

import com.bpawlowski.domain.Result
import com.bpawlowski.domain.failure
import com.bpawlowski.domain.success
import com.bpawlowski.domain.exception.FallDetectorException
import com.bpawlowski.domain.model.Sensitivity
import com.bpawlowski.domain.model.ServiceState
import com.bpawlowski.data.datasource.ServiceStateLocalDataSource
import com.bpawlowski.domain.repository.ServiceStateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class ServiceStateRepositoryImpl(
    private val serviceStateLocalDataSource: ServiceStateLocalDataSource
) : ServiceStateRepository {

    override fun isRunning(): Flow<Boolean> =
        serviceStateLocalDataSource.getIsRunningFlow()

    override fun getSensitivityFlow(): Flow<Sensitivity> =
        serviceStateLocalDataSource.getSensitivityFlow()

    override suspend fun initiateState() {
        if (serviceStateLocalDataSource.get() == null) {
            withContext(Dispatchers.IO) {
                serviceStateLocalDataSource.initiateState()
            }
        }
    }

    override suspend fun getIsRunningFlag(): Result<Boolean> {
        val isRunning = serviceStateLocalDataSource.getIsRunning()
        return if (isRunning != null) {
            success(isRunning)
        } else {
            failure(FallDetectorException.NoRecordsException)
        }
    }

    override suspend fun updateSensitivity(sensitivity: Sensitivity): Result<Unit> {
        val updated = serviceStateLocalDataSource.updateSensitivity(sensitivity)
        return if (updated != 0) {
            success(Unit)
        } else {
            failure(FallDetectorException.StateNotInitializedException)
        }
    }

    override suspend fun updateIsRunning(isRunning: Boolean): Result<Unit> {
        val updated = serviceStateLocalDataSource.updateIsRunning(isRunning)
        return if (updated != 0) {
            success(Unit)
        } else {
            failure(FallDetectorException.StateNotInitializedException)
        }
    }

    override suspend fun updateState(serviceState: ServiceState): Result<Unit> {
        val updated = serviceStateLocalDataSource.update(serviceState)
        return if (updated != 0) {
            success(Unit)
        } else {
            failure(FallDetectorException.StateNotInitializedException)
        }
    }

    override suspend fun getServiceState(): Result<ServiceState> {
        val state = serviceStateLocalDataSource.get()
        return if (state != null) {
            success(state)
        } else {
            failure(FallDetectorException.StateNotInitializedException)
        }
    }
}
