package com.example.bpawlowski.falldetector.screens.contacts.details

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.service.LocationProvider
import com.bpawlowski.domain.service.ConnectivityService
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import kotlinx.coroutines.launch

class ContactDetailsViewModel(
    private val contactsRepository: com.bpawlowski.domain.repository.ContactRepository,
    private val connectivityService: ConnectivityService,
    private val locationProvider: LocationProvider,
    initialState: ContactDetailsViewState = ContactDetailsViewState()
) : BaseViewModel<ContactDetailsViewState>(initialState) {

    fun initData(id: Long) = viewModelScope.launch {
        contactsRepository.getContact(id)
            .onSuccess { setState { copy(contact = success(it)) } }
    }

    fun updateContact(contact: Contact) = backgroundScope.launch {
        setState { copy(saveContactRequest = Loading) }

        contactsRepository.updateContact(contact)
            .onSuccess {
                setState {
                    copy(
                        saveContactRequest = success(Unit),
                        contact = success(contact)
                    )
                }
            }
            .onFailure { setState { copy(saveContactRequest = failure(it)) } }
    }

    fun updatePhotoPath(filePath: String) {
        // TODO handle this
    }

    fun sendSms() = backgroundScope.launch {
        locationProvider.getLastKnownLocation().onSuccess {
            val contact = currentState.contact() ?: return@launch
            connectivityService.sendMessage(contact.mobile, it)
        }
    }

    fun callContact() {
        val contact = currentState.contact() ?: return
        connectivityService.call(contact)
    }
}
