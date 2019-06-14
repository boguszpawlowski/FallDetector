package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.database.catching
import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.exceptions.FallDetectorException
import bogusz.com.service.database.failure
import bogusz.com.service.database.repository.base.BaseRepository
import bogusz.com.service.database.success
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import bogusz.com.service.util.sortedBy
import javax.inject.Inject

internal class ContactRepositoryImpl @Inject constructor(
    databaseService: DatabaseService
) : BaseRepository(), ContactRepository {

    private val contactDao by lazy { databaseService.getContactDao() }

    override suspend fun addContact(contact: Contact): FallDetectorResult<Long> =
        catching {
            val iceContactId = contactDao.findIceContact()
            if (contact.priority == UserPriority.PRIORITY_ICE && iceContactId != null && contact.id != iceContactId) {
                failure(FallDetectorException.IceAlreadyExistsException)
            } else {
                success(contactDao.insert(contact))
            }
        }

    override suspend fun getContact(id: Long): FallDetectorResult<Contact> =
        catching {
            val contact = contactDao.getContactById(id)
            if (contact != null) {
                success(contact)
            } else {
                failure(FallDetectorException.NoSuchContactException)
            }
        }

    override fun getAllContactsData(): LiveData<List<Contact>> =
        contactDao.getAllData().sortedBy { it.priority }

    override suspend fun updateContactEmail(contact: Contact): FallDetectorResult<Int> {
        val id = contact.id ?: throw IllegalArgumentException("No contact id")
        val email = contact.email ?: throw IllegalArgumentException("No contact email")

        return catching {
            val columnsAffected = contactDao.updateEmail(id, email)
            if (columnsAffected != 0) {
                success(columnsAffected)
            } else {
                failure(FallDetectorException.NoSuchContactException)
            }
        }
    }

    override suspend fun getContactByMobile(mobile: Int): FallDetectorResult<Contact> =
        catching {
            val contact = contactDao.getContactByMobile(mobile)
            if (contact != null) {
                success(contact)
            } else {
                failure(FallDetectorException.NoSuchContactException)
            }
        }

    override suspend fun getAllContacts(): FallDetectorResult<List<Contact>> =
        catching {
            val contacts = contactDao.getAll()
            if (contacts.isNotEmpty()) {
                success(contacts)
            } else {
                failure(FallDetectorException.NoContactsException)
            }
        }

    override suspend fun removeContact(contact: Contact): FallDetectorResult<Int> =
        catching {
            val columnsAffected = contactDao.delete(contact)
            if (columnsAffected != 0) {
                success(columnsAffected)
            } else {
                failure(FallDetectorException.NoSuchContactException)
            }
        }
}