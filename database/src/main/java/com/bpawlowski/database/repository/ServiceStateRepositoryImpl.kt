package com.bpawlowski.database.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.domain.failure
import com.bpawlowski.core.domain.success
import com.bpawlowski.core.exception.FallDetectorException
import com.bpawlowski.database.dbservice.DatabaseService
import com.bpawlowski.core.model.Sensitivity
import com.bpawlowski.database.entity.ServiceStateDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

internal class ServiceStateRepositoryImpl(
	databaseService: DatabaseService
) : ServiceStateRepository {

	private val serviceStateDao by lazy { databaseService.getServiceStateDao() }

	override fun getIsRunningData(): LiveData<Boolean> =
		serviceStateDao.getIsRunningData()

	override fun getSensitivityData(): LiveData<Sensitivity> =
		serviceStateDao.getSensitivityData()

	override suspend fun initiateState() {
		if (serviceStateDao.getServiceState() == null) {
		 	withContext(Dispatchers.IO) {
				try {
					serviceStateDao.initiateState(ServiceStateDb())
				} catch (e: Exception) {
					Timber.e(e)
				}
			}
		}
	}

	override suspend fun getIsRunningFlag(): Result<Boolean> {
		val isRunning = serviceStateDao.getIsRunningFlag()
		return if (isRunning != null) {
			success(isRunning)
		} else {
			failure(FallDetectorException.NoRecordsException)
		}
	}

	override suspend fun updateSensitivity(sensitivity: Sensitivity): Result<Unit> {
		val updated = serviceStateDao.updateSensitivity(sensitivity)
		return if (updated != 0) {
			success(Unit)
		} else {
			failure(FallDetectorException.StateNotInitializedException)
		}
	}

	override suspend fun updateIsRunning(isRunning: Boolean): Result<Unit> {
		val updated = serviceStateDao.updateIsRunningFlag(isRunning)
		return if (updated != 0) {
			success(Unit)
		} else {
			failure(FallDetectorException.StateNotInitializedException)
		}
	}

	override suspend fun updateState(serviceState: ServiceStateDb): Result<Unit> {
		val updated = serviceStateDao.updateServiceState(serviceState)
		return if (updated != 0) {
			success(Unit)
		} else {
			failure(FallDetectorException.StateNotInitializedException)
		}
	}

	override suspend fun getServiceState(): Result<ServiceStateDb> {
		val state = serviceStateDao.getServiceState()
		return if(state !=null){
			success(state)
		} else {
			failure(FallDetectorException.StateNotInitializedException)
		}
	}
}