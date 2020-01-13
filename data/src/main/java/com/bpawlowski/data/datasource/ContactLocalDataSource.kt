package com.bpawlowski.data.datasource

import com.bpawlowski.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactLocalDataSource {
    fun getAllFlow(): Flow<List<Contact>>
    suspend fun getAll(): List<Contact>
    suspend fun insert(contact: Contact): Long
    suspend fun getById(contactId: Long): Contact?
    suspend fun update(contact: Contact): Int
    suspend fun updateEmail(contactId: Long, email: String): Int
    suspend fun updatePhotoPath(contactId: Long, photoPath: String): Int
    suspend fun delete(contact: Contact): Int
    suspend fun findIceContact(): Contact?
    suspend fun getContactByMobile(mobile: Int): Contact?
}
