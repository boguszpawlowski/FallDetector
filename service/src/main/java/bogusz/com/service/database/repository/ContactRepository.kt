package bogusz.com.service.database.repository

import bogusz.com.service.model.Contact
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ContactRepository {
    fun addContact(contact: Contact): Single<Long>

    fun getAllContacts(): Flowable<List<Contact>>

    fun fetchAllContacts(): Single<List<Contact>>

    fun getContactByMobile(mobile: Int): Single<Contact>

    fun updateContactEmail(contact: Contact): Completable

    fun removeContact(contact: Contact): Completable

    fun isIceContactExisting(): Single<Boolean>
}