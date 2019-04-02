package com.example.bpawlowski.falldetector.presentation.activity.main.contacts

import com.example.bpawlowski.falldetector.presentation.activity.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.presentation.util.doNothing
import com.example.bpawlowski.falldetector.service.database.repository.IContactRepository
import com.example.bpawlowski.falldetector.service.model.Contact
import com.example.bpawlowski.falldetector.service.rx.ISchedulerProvider
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: IContactRepository,
    private val schedulerProvider: ISchedulerProvider
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
            contactsRepository.isIceContactExisting(contact)
                .flatMap { contactsRepository.addContact(contact) }
                .observeOn(schedulerProvider.MAIN)
                .subscribe(
                    { doNothing },
                    { contactSubject::onError }
                )
        )
    }

    companion object {
        val TAG = ContactsViewModel::class.java.simpleName
    }
}