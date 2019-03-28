package com.example.bpawlowski.falldetector.service.database.repository

import com.example.bpawlowski.falldetector.presentation.di.annotation.AppScope
import com.example.bpawlowski.falldetector.service.database.dbservice.IDatabaseService
import com.example.bpawlowski.falldetector.service.database.repository.base.BaseRepository
import com.example.bpawlowski.falldetector.service.model.Contact
import com.example.bpawlowski.falldetector.service.model.UserPriority
import com.example.bpawlowski.falldetector.service.rx.ISchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AppScope
class ContactRepository @Inject constructor(
    databaseService: IDatabaseService,
    private val schedulerProvider: ISchedulerProvider
) : BaseRepository() {

    private val contactDao = databaseService.getContactDao()

    fun addContact(contact: Contact): Single<Long> =
        contactDao.insert(contact).subscribeOn(schedulerProvider.IO)

    fun getAllContacts(): Flowable<List<Contact>> =
        contactDao.getAll().subscribeOn(schedulerProvider.IO)

    fun getContactByMobile(mobile: Int): Single<Contact> =
        contactDao.getContactByMobile(mobile).subscribeOn(schedulerProvider.IO)

    fun updateContactEmail(contact: Contact): Completable {
        val id = contact.id ?: return Completable.error(Throwable("No contact id"))
        val email = contact.email ?: return Completable.error(Throwable("No contact email"))

        return contactDao.updateEmail(id, email)
            .subscribeOn(schedulerProvider.IO)
    }

    fun removeContact(contact: Contact): Completable =
        contactDao.delete(contact).subscribeOn(schedulerProvider.IO)

    fun isIceContactExisting(contact: Contact): Single<Boolean> =
        contactDao.findIceContact()
            .map { contact.priority == UserPriority.PRIORITY_ICE }
            .onErrorResumeNext { Single.just(false) }
            .observeOn(schedulerProvider.IO)

    companion object {
        val TAG = ContactRepository::class.java.name
    }
}