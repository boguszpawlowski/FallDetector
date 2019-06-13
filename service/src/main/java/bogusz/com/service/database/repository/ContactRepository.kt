package bogusz.com.service.database.repository

import androidx.lifecycle.LiveData
import bogusz.com.service.model.Contact
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ContactRepository {
    suspend fun addContact(contact: Contact): Long

    suspend fun getContact(id: Long): Contact

    fun getAllContactsData(): LiveData<List<Contact>>

    suspend fun getAllContacts(): List<Contact>

    suspend fun getContactByMobile(mobile: Int): Contact

    suspend fun updateContactEmail(contact: Contact): Int

    suspend fun removeContact(contact: Contact): Int

    suspend fun isIceContactExisting(): Boolean
}