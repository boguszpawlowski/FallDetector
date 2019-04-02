package com.example.bpawlowski.falldetector.presentation.activity.main.sms

import com.example.bpawlowski.falldetector.presentation.activity.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.service.database.repository.ContactRepository
import com.example.bpawlowski.falldetector.service.model.Contact
import com.example.bpawlowski.falldetector.service.rx.ISchedulerProvider
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
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
}