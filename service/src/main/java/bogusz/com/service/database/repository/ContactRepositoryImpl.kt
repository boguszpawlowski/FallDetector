package bogusz.com.service.database.repository

import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.repository.base.BaseRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

internal class ContactRepositoryImpl @Inject constructor(
    databaseService: DatabaseService,
    private val schedulerProvider: SchedulerProvider
) : BaseRepository(), ContactRepository {

    private val contactDao by lazy { databaseService.getContactDao() }

    override fun addContact(contact: Contact): Single<Long> =
        contactDao.insert(contact)
            .subscribeOn(schedulerProvider.IO)

    override fun getAllContacts(): Flowable<List<Contact>> =
        contactDao.getAll()
            .subscribeOn(schedulerProvider.IO)
            .map { it.sortedBy { it.priority } }

    override fun getContactByMobile(mobile: Int): Single<Contact> =
        contactDao.getContactByMobile(mobile)
            .subscribeOn(schedulerProvider.IO)

    override fun updateContactEmail(contact: Contact): Completable {
        val id = contact.id ?: return Completable.error(Throwable("No contact id"))
        val email = contact.email ?: return Completable.error(Throwable("No contact email"))

        return contactDao.updateEmail(id, email)
            .subscribeOn(schedulerProvider.IO)
    }

    override fun removeContact(contact: Contact): Completable =
        contactDao.delete(contact)
            .subscribeOn(schedulerProvider.IO)

    override fun isIceContactExisting(): Single<Boolean> =
        contactDao.findIceContact()
            .subscribeOn(schedulerProvider.IO)
            .map { true }
            .onErrorResumeNext { Single.just(false) }
}