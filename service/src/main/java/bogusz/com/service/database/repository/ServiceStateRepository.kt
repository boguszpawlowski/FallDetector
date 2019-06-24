package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.model.Sensitivity

interface ServiceStateRepository {

    suspend fun initiateState()

    fun getIsRunningData(): LiveData<Boolean>

    fun getSensitivityData(): LiveData<Sensitivity>

    suspend fun getIsRunningFlag(): FallDetectorResult<Boolean>

    suspend fun updateSensitivity(sensitivity: Sensitivity): FallDetectorResult<Unit>

    suspend fun updateIsRunning(isRunning: Boolean): FallDetectorResult<Unit>
}