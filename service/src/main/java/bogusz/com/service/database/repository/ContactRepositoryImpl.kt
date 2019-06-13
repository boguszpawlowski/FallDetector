package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.repository.base.BaseRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.util.sortedBy
import javax.inject.Inject

internal class ContactRepositoryImpl @Inject constructor(
    databaseService: DatabaseService
) : BaseRepository(), ContactRepository {

    private val contactDao by lazy { databaseService.getContactDao() } //TODO convert all to Result

    override suspend fun addContact(contact: Contact): Long =
        contactDao.insert(contact)

    override suspend fun getContact(id: Long): Contact =
        contactDao.getContactById(id)

    override fun getAllContactsData(): LiveData<List<Contact>> =
        contactDao.getAllData().sortedBy { it.priority }

    override suspend fun updateContactEmail(contact: Contact): Int {
        val id = contact.id ?: throw IllegalArgumentException("No contact id")
        val email = contact.email ?: throw IllegalArgumentException("No contact email")

        return contactDao.updateEmail(id, email)
    }

    override suspend fun getContactByMobile(mobile: Int): Contact =
        contactDao.getContactByMobile(mobile)

    override suspend fun getAllContacts(): List<Contact> =
        contactDao.getAll()

    override suspend fun removeContact(contact: Contact): Int =
        contactDao.delete(contact)

    override suspend fun isIceContactExisting(): Boolean =
        contactDao.findIceContact() != null
}