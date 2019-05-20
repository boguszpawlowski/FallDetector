package com.example.bpawlowski.falldetector.ui.main.sms

import android.content.Context
import bogusz.com.service.connectivity.SmsService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.model.Contact
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.toast
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val schedulerProvider: SchedulerProvider,
    private val locationProvider: LocationProvider,
    private val smsService: SmsService,
    private val context: Context
) : BaseViewModel() {

    val contactsSubject = BehaviorSubject.create<List<Contact>>()

    init {
        getAllContacts()
    }

    fun sendMessage(contact: Contact) =
        disposable.add(
            locationProvider.getLastKnownLocation()
                .subscribeOn(schedulerProvider.IO)
                .subscribe(
                    { location ->
                        smsService.sendMessage(contact.mobile, location)
                        context.toast("Message sent") //TODO replace with snackbar
                    },
                    contactsSubject::onError
                )
        )

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