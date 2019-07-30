package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.model.Contact

interface ContactRepository {
    fun getAllContactsData(): LiveData<List<Contact>>

    suspend fun getAllContacts(): FallDetectorResult<List<Contact>>

    suspend fun addContact(contact: Contact): FallDetectorResult<Long>

    suspend fun getContact(id: Long): FallDetectorResult<Contact>

    suspend fun getContactByMobile(mobile: Int): FallDetectorResult<Contact>

	suspend fun updateContact(contact: Contact): FallDetectorResult<Int>

	suspend fun updateContactEmail(contactId: Long, email: String): FallDetectorResult<Int>

	suspend fun updateContactPhotoPath(contactId: Long, photoPath: String): FallDetectorResult<Int>

	suspend fun removeContact(contact: Contact): FallDetectorResult<Contact>
}