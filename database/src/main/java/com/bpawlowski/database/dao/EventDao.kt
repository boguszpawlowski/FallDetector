package com.bpawlowski.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bpawlowski.database.entity.EventDb
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao : BaseDao<EventDb> {

    @Query("SELECT * FROM event")
    fun getAllFlow(): Flow<List<EventDb>>

    @Query("SELECT * FROM event")
    suspend fun getAll(): List<EventDb>

    @Query("SELECT * FROM event WHERE Id = :eventId")
    suspend fun getEventById(eventId: Long): EventDb?
}