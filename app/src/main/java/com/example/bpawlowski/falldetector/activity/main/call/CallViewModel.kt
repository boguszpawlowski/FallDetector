package com.example.bpawlowski.falldetector.activity.main.call

import android.content.Context
import bogusz.com.service.connectivity.ICallService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.ISchedulerProvider
import com.example.bpawlowski.falldetector.activity.base.activity.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CallViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val callService: ICallService,
    private val schedulerProvider: ISchedulerProvider
) : BaseViewModel() {

    val contactsSubject = BehaviorSubject.create<List<Contact>>()

    override fun onResume() {
        super.onResume()
        getAllContacts()
    }

    private fun getAllContacts() {
        disposable.add(
            contactRepository.getAllContacts()
                .observeOn(schedulerProvider.MAIN)
                .subscribe(
                    contactsSubject::onNext,
                    contactsSubject::onError,
                    contactsSubject::onComplete
                )
        )
    }

    fun callContact(context: Context, contact: Contact) = callService.call(context, contact)
}