package com.bpawlowski.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bpawlowski.database.entity.ContactDb
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ContactDao {

	@Query("SELECT * FROM contact")
	fun getAllData(): Flow<List<ContactDb>>

	@Query("SELECT * FROM contact WHERE id like :id")
	suspend fun getContactById(id: Long): ContactDb?

	@Query("SELECT * FROM contact")
	suspend fun getAll(): List<ContactDb>

	@Query("SELECT * FROM contact WHERE mobile LIKE :mobile")
	suspend fun getContactByMobile(mobile: Int): ContactDb?

	@Insert(onConflict = OnConflictStrategy.ABORT)
	suspend fun insert(contact: ContactDb): Long

	@Delete
	suspend fun delete(contact: ContactDb): Int

	@Update
	suspend fun update(contact: ContactDb): Int

	@Query(value = "UPDATE contact SET email=:email WHERE id like :id")
	suspend fun updateEmail(id: Long, email: String): Int

	@Query(value = "UPDATE contact SET photo_path=:photoPath WHERE id like :id")
	suspend fun updatePhotoPath(id: Long, photoPath: String): Int

	@Query(value = "SELECT id FROM contact WHERE user_priority = 1")
	suspend fun findIceContact(): Long?
}