package bogusz.com.service.database.repository

import bogusz.com.service.database.dbservice.IDatabaseService
import bogusz.com.service.database.repository.base.BaseRepository
import bogusz.com.service.di.AppScope
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import bogusz.com.service.rx.ISchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@AppScope
class ContactRepository @Inject constructor(
    databaseService: IDatabaseService,
    private val schedulerProvider: ISchedulerProvider
) : BaseRepository(), IContactRepository {

    private val contactDao = databaseService.getContactDao()

    override fun addContact(contact: Contact): Single<Long> =
        contactDao.insert(contact).subscribeOn(schedulerProvider.IO)

    override fun getAllContacts(): Flowable<List<Contact>> =
        contactDao.getAll().subscribeOn(schedulerProvider.IO)

    override fun getContactByMobile(mobile: Int): Single<Contact> =
        contactDao.getContactByMobile(mobile).subscribeOn(schedulerProvider.IO)

    override fun updateContactEmail(contact: Contact): Completable {
        val id = contact.id ?: return Completable.error(Throwable("No contact id"))
        val email = contact.email ?: return Completable.error(Throwable("No contact email"))

        return contactDao.updateEmail(id, email)
            .subscribeOn(schedulerProvider.IO)
    }

    override fun removeContact(contact: Contact): Completable =
        contactDao.delete(contact).subscribeOn(schedulerProvider.IO)

    override fun isIceContactExisting(contact: Contact): Single<Boolean> =
        contactDao.findIceContact()
            .map { contact.priority == UserPriority.PRIORITY_ICE }
            .onErrorResumeNext { Single.just(false) }
            .observeOn(schedulerProvider.IO)

    companion object {
        val TAG = ContactRepository::class.java.name
    }
}