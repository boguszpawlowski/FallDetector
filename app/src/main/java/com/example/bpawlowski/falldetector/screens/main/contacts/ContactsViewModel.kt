package com.example.bpawlowski.falldetector.screens.main.contacts

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.repository.ContactRepository
import com.bpawlowski.domain.service.CallService
import com.bpawlowski.domain.service.LocationProvider
import com.bpawlowski.domain.service.TextMessageService
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactsViewModel(
    state: ContactsViewState = ContactsViewState(),
    private val contactsRepository: ContactRepository,
    private val textMessageService: TextMessageService,
    private val callService: CallService,
    private val locationProvider: LocationProvider
) : BaseViewModel<ContactsViewState>(state) {

    init {
        viewModelScope.launch {
            contactsRepository.getAllContactsFlow().collect {
                setState {
                    copy(contacts = success(it))
                }
            }
        }
    }

    fun removeContact(contactId: Long) = backgroundScope.launch {

        setState { copy(removeContactRequest = Loading) }

        contactsRepository.removeContact(contactId)
            .onSuccess {
                setState {
                    copy(removeContactRequest = success(it))
                }
            }.onFailure {
                setState {
                    copy(removeContactRequest = failure(it))
                }
            }
    }

    fun addContact(contact: Contact) = backgroundScope.launch {
        contactsRepository.addContact(contact)
            .onException { Timber.e(it) }
    }

    fun callContact(contact: Contact) = callService.call(contact)

    fun sendMessage(contact: Contact) = backgroundScope.launch {
        locationProvider.getLastKnownLocation().onSuccess {
            textMessageService.sendMessage(contact.mobile, it)
        }
    }
}
