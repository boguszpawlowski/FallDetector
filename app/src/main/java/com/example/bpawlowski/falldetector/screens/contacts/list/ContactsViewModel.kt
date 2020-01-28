package com.example.bpawlowski.falldetector.screens.contacts.list

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.repository.ContactRepository
import com.bpawlowski.domain.service.ConnectivityService
import com.bpawlowski.domain.service.LocationProvider
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactsViewModel(
    private val contactsRepository: ContactRepository,
    private val connectivityService: ConnectivityService,
    private val locationProvider: LocationProvider,
    initialState: ContactsViewState = ContactsViewState()
) : BaseViewModel<ContactsViewState>(initialState) {

    init {
        viewModelScope.launch {
            contactsRepository.getAllContactsFlow().collect {
                setState {
                    copy(contacts = it)
                }
            }
        }
    }

    fun removeContact(contact: Contact) = backgroundScope.launch {
        val contactId = contact.id ?: error("Id shouldn't be null")

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

    fun callContact(contact: Contact) = connectivityService.call(contact)

    fun sendMessage(contact: Contact) = backgroundScope.launch {
        locationProvider.getLastKnownLocation().onSuccess {
            connectivityService.sendMessage(contact.mobile, it)
        }
    }
}
