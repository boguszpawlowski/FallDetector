package com.bpawlowski.database.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.core.domain.FallDetectorResult
import com.bpawlowski.core.domain.failure
import com.bpawlowski.core.domain.success
import com.bpawlowski.core.exception.FallDetectorException
import com.bpawlowski.core.model.Contact
import com.bpawlowski.core.model.ContactPriority
import com.bpawlowski.database.dbservice.DatabaseService
import com.bpawlowski.database.util.map
import com.bpawlowski.database.util.sortedByDescending
import com.bpawlowski.database.util.toContact
import com.bpawlowski.database.util.toContactDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ContactRepositoryImpl(
	databaseService: DatabaseService
) : ContactRepository {

	private val contactDao by lazy { databaseService.getContactDao() }

	override suspend fun addContact(contact: Contact): FallDetectorResult<Long> = withContext(Dispatchers.IO) {
		checkContact(contact).map {
			contactDao.insert(contact.toContactDb())
		}
	}

	override suspend fun getContact(id: Long): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		val contact = contactDao.getContactById(id)?.toContact()
		if (contact != null) {
			success(contact)
		} else {
			failure(FallDetectorException.NoSuchRecordException(id))
		}
	}

	override fun getAllContactsData(): LiveData<List<Contact>> =
		contactDao.getAllData().map { it.map { it.toContact() } }.sortedByDescending { it.priority }

	override suspend fun updateContact(contact: Contact): FallDetectorResult<Unit> = withContext(Dispatchers.IO) {
		checkContact(contact).flatMap {
			if (contactDao.update(contact.toContactDb()) != 0) {
				success(Unit)
			} else {
				failure(FallDetectorException.RecordNotUpdatedException(contact.id))
			}
		}
	}

	override suspend fun updateContactEmail(contactId: Long, email: String): FallDetectorResult<Int> = withContext(Dispatchers.IO) {
		val columnsAffected = contactDao.updateEmail(contactId, email)
		if (columnsAffected != 0) {
			success(columnsAffected)
		} else {
			failure(FallDetectorException.NoSuchRecordException(contactId))
		}
	}

	override suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): FallDetectorResult<Int> = withContext(Dispatchers.IO) {
		val columnsAffected = contactDao.updatePhotoPath(contactId, photoPath)
		if (columnsAffected != 0) {
			success(columnsAffected)
		} else {
			failure(FallDetectorException.NoSuchRecordException(contactId))
		}
	}

	override suspend fun getContactByMobile(mobile: Int): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		val contact = contactDao.getContactByMobile(mobile)?.toContact()
		if (contact != null) {
			success(contact)
		} else {
			failure(FallDetectorException.InvalidMobileException(mobile))
		}
	}

	override suspend fun getAllContacts(): FallDetectorResult<List<Contact>> = withContext(Dispatchers.IO) {
		val contacts = contactDao.getAll().map { it.toContact() }
		if (contacts.isNotEmpty()) {
			success(contacts)
		} else {
			failure(FallDetectorException.NoRecordsException)
		}
	}

	override suspend fun removeContact(contact: Contact): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		val columnsAffected = contactDao.delete(contact.toContactDb())
		if (columnsAffected != 0) {
			success(contact)
		} else {
			failure(FallDetectorException.NoSuchRecordException(contact.id))
		}
	}

	private suspend fun checkContact(contact: Contact): FallDetectorResult<Unit> = withContext(Dispatchers.IO) {
		val iceContactId = contactDao.findIceContact()
		val contactWithSameMobile = contactDao.getContactByMobile(contact.mobile)
		if (contact.priority == ContactPriority.PRIORITY_ICE
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