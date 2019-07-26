package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.database.catchIO
import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.exceptions.FallDetectorException
import bogusz.com.service.database.failure
import bogusz.com.service.database.success
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import bogusz.com.service.util.sortedByDescending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ContactRepositoryImpl(
	databaseService: DatabaseService
) : ContactRepository {

	private val contactDao by lazy { databaseService.getContactDao() }

	override suspend fun addContact(contact: Contact): FallDetectorResult<Long> = withContext(Dispatchers.IO) {
		catchIO {
			val iceContactId = contactDao.findIceContact()
			if (contact.priority == UserPriority.PRIORITY_ICE && iceContactId != null && contact.id != iceContactId) {
				failure(FallDetectorException.IceAlreadyExistsException)
			} else {
				success(contactDao.insert(contact))
			}
		}
	}

	override suspend fun getContact(id: Long): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		catchIO {
			val contact = contactDao.getContactById(id)
			if (contact != null) {
				success(contact)
			} else {
				failure(FallDetectorException.NoSuchRecordException())
			}
		}
	}

	override fun getAllContactsData(): LiveData<List<Contact>> =
		contactDao.getAllData().sortedByDescending { it.priority }

	override suspend fun updateContactEmail(contactId: Long, email: String): FallDetectorResult<Int> = withContext(Dispatchers.IO) {
		catchIO {
			val columnsAffected = contactDao.updateEmail(contactId, email)
			if (columnsAffected != 0) {
				success(columnsAffected)
			} else {
				failure(FallDetectorException.NoSuchRecordException(contactId))
			}
		}
	}

	override suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): FallDetectorResult<Int> = withContext(Dispatchers.IO) {
		catchIO {
			val columnsAffected = contactDao.updatePhotoPath(contactId, photoPath)
			if (columnsAffected != 0) {
				success(columnsAffected)
			} else {
				failure(FallDetectorException.NoSuchRecordException(contactId))
			}
		}
	}

	override suspend fun getContactByMobile(mobile: Int): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		catchIO {
			val contact = contactDao.getContactByMobile(mobile)
			if (contact != null) {
				success(contact)
			} else {
				failure(FallDetectorException.NoSuchRecordException())
			}
		}
	}

	override suspend fun getAllContacts(): FallDetectorResult<List<Contact>> = withContext(Dispatchers.IO) {
		catchIO {
			val contacts = contactDao.getAll()
			if (contacts.isNotEmpty()) {
				success(contacts)
			} else {
				failure(FallDetectorException.NoRecordsException)
			}
		}
	}

	override suspend fun removeContact(contact: Contact): FallDetectorResult<Contact> = withContext(Dispatchers.IO) {
		catchIO {
			val columnsAffected = contactDao.delete(contact)
			if (columnsAffected != 0) {
				success(contact)
			} else {
				failure(FallDetectorException.NoSuchRecordException())
			}
		}
	}
}