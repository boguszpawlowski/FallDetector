package com.bpawlowski.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<E> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg entity: E): List<Long>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: E): Long

	@Delete
	suspend fun delete(vararg entity: E): Int

    @Delete
    suspend fun delete(entity: E): Int

	@Update
	suspend fun update(vararg entity: E): Int

    @Update
    suspend fun update(entity: E): Int
}