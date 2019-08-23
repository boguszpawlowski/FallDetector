package com.bpawlowski.database.repository

import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
	fun getAllContactsFlow(): Flow<List<Contact>>

    suspend fun getAllContacts(): Result<List<Contact>>

    suspend fun addContact(contact: Contact): Result<Long>

    suspend fun getContact(id: Long): Result<Contact>

    suspend fun getContactByMobile(mobile: Int): Result<Contact>

	suspend fun updateContact(contact: Contact): Result<Unit>

	suspend fun updateContactEmail(contactId: Long, email: String): Result<Int>

	suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): Result<Int>

	suspend fun removeContact(contact: Contact): Result<Contact>
}