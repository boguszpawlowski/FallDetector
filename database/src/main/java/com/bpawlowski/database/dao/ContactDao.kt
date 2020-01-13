package com.bpawlowski.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bpawlowski.database.entity.ContactDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao : BaseDao<ContactDb> {

    @Query("SELECT * FROM contact")
    fun getAllFlow(): Flow<List<ContactDb>>

    @Query("SELECT * FROM contact WHERE id like :id")
    suspend fun getContactById(id: Long): ContactDb?

    @Query("SELECT * FROM contact")
    suspend fun getAll(): List<ContactDb>

    @Query("SELECT * FROM contact WHERE mobile LIKE :mobile")
    suspend fun getContactByMobile(mobile: Int): ContactDb?

    @Query(value = "UPDATE contact SET email=:email WHERE id like :id")
    suspend fun updateEmail(id: Long, email: String): Int

    @Query(value = "UPDATE contact SET photo_path=:photoPath WHERE id like :id")
    suspend fun updatePhotoPath(id: Long, photoPath: String): Int

    @Query(value = "SELECT * FROM contact WHERE user_priority = 1")
    suspend fun findIceContact(): ContactDb?
}
