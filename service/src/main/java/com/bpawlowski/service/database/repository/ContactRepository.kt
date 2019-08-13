package com.bpawlowski.service.database.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.service.database.FallDetectorResult
import com.bpawlowski.service.model.Contact

interface ContactRepository {
    fun getAllContactsData(): LiveData<List<Contact>>

    suspend fun getAllContacts(): FallDetectorResult<List<Contact>>

    suspend fun addContact(contact: Contact): FallDetectorResult<Long>

    suspend fun getContact(id: Long): FallDetectorResult<Contact>

    suspend fun getContactByMobile(mobile: Int): FallDetectorResult<Contact>

	suspend fun updateContact(contact: Contact): FallDetectorResult<Unit>

	suspend fun updateContactEmail(contactId: Long, email: String): FallDetectorResult<Int>

	suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): FallDetectorResult<Int>

	suspend fun removeContact(contact: Contact): FallDetectorResult<Contact>
}