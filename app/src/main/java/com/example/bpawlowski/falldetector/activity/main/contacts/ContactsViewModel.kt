package com.example.bpawlowski.falldetector.activity.main.contacts

import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.activity.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.doNothing
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val contactSubject = BehaviorSubject.create<List<Contact>>()

    override fun onResume() {
        super.onResume()
        getAllContacts()
    }

    private fun getAllContacts() {
        disposable.add(
            contactsRepository.getAllContacts()
                .observeOn(schedulerProvider.MAIN)
                .subscribe(
                    contactSubject::onNext,
                    contactSubject::onError
                )
        )
    }

    fun removeContact(contact: Contact) {
        disposable.add(
            contactsRepository.removeContact(contact)
                .observeOn(schedulerProvider.MAIN)
                .subscribe(
                    { doNothing },
                    { contactSubject::onError }
                )
        )
    }

    fun addContact(contact: Contact) {
        disposable.add(
            contactsRepository.addContact(contact)
                .observeOn(schedulerProvider.MAIN)
                .subscribe(
                    { doNothing },
                    { contactSubject::onError }
                )
        )
    }
}