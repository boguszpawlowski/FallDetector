package com.bpawlowski.database.repository

import androidx.lifecycle.LiveData
import com.bpawlowski.database.domain.FallDetectorResult
import com.bpawlowski.database.dbservice.DatabaseService
import com.bpawlowski.database.exceptions.FallDetectorException
import com.bpawlowski.database.domain.failure
import com.bpawlowski.database.domain.success
import com.bpawlowski.database.entity.Contact
import com.bpawlowski.database.entity.UserPriority
import com.bpawlowski.database.util.sortedByDescending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ContactRepositoryImpl(
	databaseService: DatabaseService
) : ContactRepository {

	private val contactDao by lazy { databaseService.getContactDao() }

	override suspend fun addContact(contact: Contact): FallDetectorResult<Long> = withContext(Dispatchers.IO) {
		if (isContactNotValid(contact)) {
			failure(FallDetectorException.IceAlreadyExistsException)
		} else {
			if (contactDao.getContactByMobile(contact.mobile) != null) {
				failure(FallDetectorException.MobileAlreadyExisting(contact.mobile))
			} else {
				success(contactDao.insert(contact))
			}
		}
	}

	override suspend fun getContact(id: Long): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		val contact = contactDao.getContactById(id)
		if (contact != null) {
			success(contact)
		} else {
			failure(FallDetectorException.NoSuchRecordException())
		}
	}

	override fun getAllContactsData(): LiveData<List<Contact>> =
		contactDao.getAllData().sortedByDescending { it.priority }

	override suspend fun updateContact(contact: Contact): FallDetectorResult<Unit> = withContext(Dispatchers.IO) {
		when {
			isContactNotValid(contact) -> failure(FallDetectorException.IceAlreadyExistsException)
			contactDao.getContactByMobile(contact.mobile) != null -> failure(
				FallDetectorException.MobileAlreadyExisting(
					contact.mobile
				)
			)

			else -> {
				if (contactDao.update(contact) != 0) {
					success(Unit)
				} else {
					failure(FallDetectorException.NoSuchRecordException())
				}
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
		val contact = contactDao.getContactByMobile(mobile)
		if (contact != null) {
			success(contact)
		} else {
			failure(FallDetectorException.NoSuchRecordException())
		}
	}

	override suspend fun getAllContacts(): FallDetectorResult<List<Contact>> = withContext(Dispatchers.IO) {
		val contacts = contactDao.getAll()
		if (contacts.isNotEmpty()) {
			success(contacts)
		} else {
			failure(FallDetectorException.NoRecordsException)
		}
	}

	override suspend fun removeContact(contact: Contact): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		val columnsAffected = contactDao.delete(contact)
		if (columnsAffected != 0) {
			success(contact)
		} else {
			failure(FallDetectorException.NoSuchRecordException())
		}
	}

	private suspend fun isContactNotValid(contact: Contact): Boolean = withContext(Dispatchers.IO) {
		val iceContactId = contactDao.findIceContact()
		contact.priority == UserPriority.PRIORITY_ICE
				&& iceContactId != null
				&& contact.id != iceContactId
	}
}