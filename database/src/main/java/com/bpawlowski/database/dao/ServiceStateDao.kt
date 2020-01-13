package com.bpawlowski.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bpawlowski.domain.model.Sensitivity
import com.bpawlowski.database.entity.ServiceStateDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceStateDao : BaseDao<ServiceStateDb> {

    @Query("SELECT * FROM service_state LIMIT 1")
    suspend fun getServiceState(): ServiceStateDb?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun initiateState(serviceState: ServiceStateDb): Long

    @Query("SELECT sensitivity FROM service_state LIMIT 1")
    fun getSensitivityFlow(): Flow<Sensitivity>

    @Query("SELECT is_running FROM service_state LIMIT 1")
    fun getIsRunningFlow(): Flow<Boolean>

    @Query("SELECT is_running FROM service_state LIMIT 1")
    suspend fun getIsRunningFlag(): Boolean?

    @Query("UPDATE service_state SET is_running=:isRunning")
    suspend fun updateIsRunningFlag(isRunning: Boolean): Int

    @Query("UPDATE service_state SET sensitivity=:sensitivity")
    suspend fun updateSensitivity(sensitivity: Sensitivity): Int

    @Update
    suspend fun updateServiceState(serviceState: ServiceStateDb): Int
}
