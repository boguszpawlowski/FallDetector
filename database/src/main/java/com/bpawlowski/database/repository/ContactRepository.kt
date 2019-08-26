package com.bpawlowski.database.repository

import com.bpawlowski.core.domain.FallDetectorResult
import com.bpawlowski.core.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
	fun getAllContactsFlow(): Flow<List<Contact>>

	suspend fun getAllContacts(): FallDetectorResult<List<Contact>>

	suspend fun addContact(contact: Contact): FallDetectorResult<Long>

	suspend fun getContact(id: Long): FallDetectorResult<Contact>

	suspend fun getContactByMobile(mobile: Int): FallDetectorResult<Contact>

	suspend fun updateContact(contact: Contact): FallDetectorResult<Unit>

	suspend fun updateContactEmail(contactId: Long, email: String): FallDetectorResult<Int>

	suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): FallDetectorResult<Int>

	suspend fun removeContact(contact: Contact): FallDetectorResult<Contact>
}