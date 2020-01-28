@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.bpawlowski.data.repository

import com.bpawlowski.data.datasource.ContactLocalDataSource
import com.bpawlowski.data.util.sortedByDescending
import com.bpawlowski.domain.Result
import com.bpawlowski.domain.exception.FallDetectorException
import com.bpawlowski.domain.failure
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.success
import kotlinx.coroutines.flow.Flow

internal class ContactRepositoryImpl(
    private val contactLocalDataSource: ContactLocalDataSource
) : com.bpawlowski.domain.repository.ContactRepository {

    override suspend fun addContact(contact: Contact): Result<Long> =
        checkContact(contact).map {
            contactLocalDataSource.insert(contact)
        }

    override suspend fun getContact(id: Long): Result<Contact> {
        val contact = contactLocalDataSource.getById(id)
        return if (contact != null) {
            success(contact)
        } else {
            failure(FallDetectorException.NoSuchRecordException(id))
        }
    }

    override fun getAllContactsFlow(): Flow<List<Contact>> =
        contactLocalDataSource.getAllFlow().sortedByDescending { it.isIce }

    override suspend fun updateContact(contact: Contact): Result<Unit> =
        checkContact(contact).flatMap {
            if (contactLocalDataSource.update(contact) != 0) {
                success(Unit)
            } else {
                failure(
                    FallDetectorException.RecordNotUpdatedException(contact.id)
                )
            }
        }

    override suspend fun updateContactEmail(contactId: Long, email: String): Result<Int> {
        val columnsAffected = contactLocalDataSource.updateEmail(contactId, email)
        return if (columnsAffected != 0) {
            success(columnsAffected)
        } else {
            failure(FallDetectorException.NoSuchRecordException(contactId))
        }
    }

    override suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): Result<Int> {
        val columnsAffected = contactLocalDataSource.updatePhotoPath(contactId, photoPath)
        return if (columnsAffected != 0) {
            success(columnsAffected)
        } else {
            failure(FallDetectorException.NoSuchRecordException(contactId))
        }
    }

    override suspend fun getContactByMobile(mobile: Int): Result<Contact> {
        val contact = contactLocalDataSource.getContactByMobile(mobile)
        return if (contact != null) {
            success(contact)
        } else {
            failure(FallDetectorException.InvalidMobileException(mobile))
        }
    }

    override suspend fun getAllContacts(): Result<List<Contact>> {
        val contacts = contactLocalDataSource.getAll()
        return if (contacts.isNotEmpty()) {
            success(contacts)
        } else {
            failure(FallDetectorException.NoRecordsException)
        }
    }

    override suspend fun removeContact(contactId: Long): Result<Contact> {
        val contact = contactLocalDataSource.getById(contactId) ?: return failure(
            FallDetectorException.NoSuchRecordException(contactId)
        )
        val columnsAffected = contactLocalDataSource.delete(contact)
        return if (columnsAffected != 0) {
            success(contact)
        } else {
            failure(FallDetectorException.RecordNotDeletedException(contactId))
        }
    }

    private suspend fun checkContact(contact: Contact): Result<Unit> {
        val iceContactId = contactLocalDataSource.findIceContact()?.id
        val contactWithSameMobile = contactLocalDataSource.getContactByMobile(contact.mobile)
        return if (contact.isIce &&
            iceContactId != null &&
            contact.id != iceContactId
        ) {
            failure(FallDetectorException.IceAlreadyExistsException)
        } else if (contactWithSameMobile != null && contactWithSameMobile.id != contact.id) {
            failure(FallDetectorException.MobileAlreadyExisting(contact.mobile))
        } else {
            success(Unit)
        }
    }
}
