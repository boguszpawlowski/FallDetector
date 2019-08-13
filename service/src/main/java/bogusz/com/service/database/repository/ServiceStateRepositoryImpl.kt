package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.exceptions.FallDetectorException
import bogusz.com.service.database.failure
import bogusz.com.service.database.success
import bogusz.com.service.model.Sensitivity
import bogusz.com.service.model.ServiceState
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
					serviceStateDao.initiateState(ServiceState())
				} catch (e: Exception) {
					Timber.e(e)
				}
			}
		}
	}

	override suspend fun getIsRunningFlag(): FallDetectorResult<Boolean> {
		val isRunning = serviceStateDao.getIsRunningFlag()
		return if (isRunning != null) {
			success(isRunning)
		} else {
			failure(FallDetectorException.NoRecordsException)
		}
	}

	override suspend fun updateSensitivity(sensitivity: Sensitivity): FallDetectorResult<Unit> {
		val updated = serviceStateDao.updateSensitivity(sensitivity)
		return if (updated != 0) {
			success(Unit)
		} else {
			failure(FallDetectorException.RecordNotUpdatedException())
		}
	}

	override suspend fun updateIsRunning(isRunning: Boolean): FallDetectorResult<Unit> {
		val updated = serviceStateDao.updateIsRunningFlag(isRunning)
		return if (updated != 0) {
			success(Unit)
		} else {
			failure(FallDetectorException.RecordNotUpdatedException())
		}
	}

	override suspend fun updateState(serviceState: ServiceState): FallDetectorResult<Unit> {
		val updated = serviceStateDao.updateServiceState(serviceState)
		return if (updated != 0) {
			success(Unit)
		} else {
			failure(FallDetectorException.RecordNotUpdatedException())
		}
	}

	override suspend fun getServiceState(): FallDetectorResult<ServiceState> {
		val state = serviceStateDao.getServiceState()
		return if(state !=null){
			success(state)
		} else {
			failure(FallDetectorException.NoSuchRecordException())
		}
	}
}