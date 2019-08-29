@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.bpawlowski.data.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.core.domain.Result
import com.bpawlowski.core.domain.failure
import com.bpawlowski.core.domain.success
import com.bpawlowski.core.exception.FallDetectorException
import com.bpawlowski.core.model.Contact
import com.bpawlowski.core.model.ContactPriority
import com.bpawlowski.data.util.toDomain
import com.bpawlowski.data.util.toEntity
import com.bpawlowski.database.dbservice.DatabaseService
import com.bpawlowski.database.util.mapList
import com.bpawlowski.database.util.sortedByDescending

internal class ContactRepositoryImpl(
	databaseService: DatabaseService
) : ContactRepository {

	private val contactDao by lazy { databaseService.getContactDao() }

	override suspend fun addContact(contact: Contact): Result<Long> =
		checkContact(contact).map {
			contactDao.insert(contact.toEntity())
		}

	override suspend fun getContact(id: Long): Result<Contact> {
		val contact = contactDao.getContactById(id)?.toDomain()
		return if (contact != null) {
			success(contact)
		} else {
			failure(FallDetectorException.NoSuchRecordException(id))
		}
	}

	override fun getAllContactsData(): LiveData<List<Contact>> =
		contactDao.getAllData()
			.mapList { it.toDomain() }
			.sortedByDescending { it.priority }

	override suspend fun updateContact(contact: Contact): Result<Unit> =
		checkContact(contact).flatMap {
			if (contactDao.update(contact.toEntity()) != 0) {
				success(Unit)
			} else {
				failure(FallDetectorException.RecordNotUpdatedException(contact.id))
			}
		}

	override suspend fun updateContactEmail(contactId: Long, email: String): Result<Int> {
		val columnsAffected = contactDao.updateEmail(contactId, email)
		return if (columnsAffected != 0) {
			success(columnsAffected)
		} else {
			failure(FallDetectorException.NoSuchRecordException(contactId))
		}
	}

	override suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): Result<Int> {
		val columnsAffected = contactDao.updatePhotoPath(contactId, photoPath)
		return if (columnsAffected != 0) {
			success(columnsAffected)
		} else {
			failure(FallDetectorException.NoSuchRecordException(contactId))
		}
	}

	override suspend fun getContactByMobile(mobile: Int): Result<Contact> {
		val contact = contactDao.getContactByMobile(mobile)?.toDomain()
		return if (contact != null) {
			success(contact)
		} else {
			failure(FallDetectorException.InvalidMobileException(mobile))
		}
	}

	override suspend fun getAllContacts(): Result<List<Contact>> {
		val contacts = contactDao.getAll().map { it.toDomain() }
		return if (contacts.isNotEmpty()) {
			success(contacts)
		} else {
			failure(FallDetectorException.NoRecordsException)
		}
	}

	override suspend fun removeContact(contact: Contact): Result<Contact> {
		val columnsAffected = contactDao.delete(contact.toEntity())
		return if (columnsAffected != 0) {
			success(contact)
		} else {
			failure(FallDetectorException.NoSuchRecordException(contact.id))
		}
	}

	private suspend fun checkContact(contact: Contact): Result<Unit> {
		val iceContactId = contactDao.findIceContact()
		val contactWithSameMobile = contactDao.getContactByMobile(contact.mobile)
		return if (contact.priority == ContactPriority.PRIORITY_ICE
			&& iceContactId != null
			&& contact.id != iceContactId
		) {
			failure(FallDetectorException.IceAlreadyExistsException)
		} else if (contactWithSameMobile != null && contactWithSameMobile.id != contact.id) {
			failure(FallDetectorException.MobileAlreadyExisting(contact.mobile))
		} else {
			success(Unit)
		}
	}
}