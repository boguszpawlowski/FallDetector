package com.example.bpawlowski.falldetector.ui.main.call

import android.content.Context
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CallViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val callService: CallService,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val contactsSubject = BehaviorSubject.create<List<Contact>>()

    override fun onResume() {
        super.onResume()
        getAllContacts()
    }

    fun callContact(context: Context, contact: Contact) = callService.call(context, contact)

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
}