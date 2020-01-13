package com.bpawlowski.database.datasource

import com.bpawlowski.domain.model.Sensitivity
import com.bpawlowski.domain.model.ServiceState
import com.bpawlowski.data.datasource.ServiceStateLocalDataSource
import com.bpawlowski.database.dao.ServiceStateDao
import com.bpawlowski.database.entity.ServiceStateDb
import com.bpawlowski.database.entity.toDomain
import com.bpawlowski.database.entity.toEntity
import kotlinx.coroutines.flow.Flow

class ServiceSateLocalDataSourceImpl(
    private val serviceStateDao: ServiceStateDao
) : ServiceStateLocalDataSource {

    override fun getIsRunningFlow(): Flow<Boolean> =
        serviceStateDao.getIsRunningFlow()

    override fun getSensitivityFlow(): Flow<Sensitivity> =
        serviceStateDao.getSensitivityFlow()

    override suspend fun update(serviceState: ServiceState): Int =
        serviceStateDao.update(serviceState.toEntity())

    override suspend fun get(): ServiceState? =
        serviceStateDao.getServiceState()?.toDomain()

    override suspend fun initiateState() =
        serviceStateDao.insert(ServiceStateDb())

    override suspend fun getIsRunning(): Boolean? =
        serviceStateDao.getIsRunningFlag()

    override suspend fun updateSensitivity(sensitivity: Sensitivity): Int =
        serviceStateDao.updateSensitivity(sensitivity)

    override suspend fun updateIsRunning(isRunning: Boolean): Int =
        serviceStateDao.updateIsRunningFlag(isRunning)
}
