package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.database.catching
import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.exceptions.FallDetectorException
import bogusz.com.service.database.failure
import bogusz.com.service.database.success
import bogusz.com.service.model.Sensitivity
import bogusz.com.service.model.ServiceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

internal class ServiceStateRepositoryImpl @Inject constructor(
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

    override suspend fun getIsRunningFlag(): FallDetectorResult<Boolean> =
        catching {
            val isRunning = serviceStateDao.getIsRunningFlag()
            if (isRunning != null) {
                success(isRunning)
            } else {
                failure(FallDetectorException.NoRecordsException)
            }
        }

    override suspend fun updateSensitivity(sensitivity: Sensitivity): FallDetectorResult<Unit> =
        catching {
            val updated = serviceStateDao.updateSensitivity(sensitivity)
            if (updated != 0) {
                success(Unit)
            } else {
                failure(FallDetectorException.RecordNotUpdatedException())
            }
        }

    override suspend fun updateIsRunning(isRunning: Boolean): FallDetectorResult<Unit> =
        catching {
            val updated = serviceStateDao.updateIsRunningFlag(isRunning)
            if (updated != 0) {
                success(Unit)
            } else {
                failure(FallDetectorException.RecordNotUpdatedException())
            }
        }
}