package com.example.bpawlowski.falldetector.ui.main.contacts

import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.doNothing
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
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
                    { Timber.i("Removed contact: $contact")  },
                    { contactSubject::onError }
                )
        )
    }
}