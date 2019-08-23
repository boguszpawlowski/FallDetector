package com.bpawlowski.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bpawlowski.database.entity.EventDb

@Dao
interface EventDao: BaseDao<EventDb>{

    @Query("SELECT * FROM event")
    fun getAllData(): LiveData<List<EventDb>>

    @Query("SELECT * FROM event")
    suspend fun getAll(): List<EventDb>

    @Query("SELECT * FROM event WHERE Id = :eventId")
    suspend fun getEventById(eventId: Long): EventDb?
}