package com.example.bpawlowski.falldetector.screens.main.details

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.service.CallService
import com.bpawlowski.domain.service.LocationProvider
import com.bpawlowski.domain.service.TextMessageService
import com.bpawlowski.domain.zip
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import com.example.bpawlowski.falldetector.domain.toContact
import com.example.bpawlowski.falldetector.domain.toForm
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContactDetailsViewModel(
    private val contactsRepository: com.bpawlowski.domain.repository.ContactRepository,
    private val textMessageService: TextMessageService,
    private val callService: CallService,
    private val locationProvider: LocationProvider,
    initialState: ContactDetailsViewState = ContactDetailsViewState()
) : BaseViewModel<ContactDetailsViewState>(initialState) {

    fun initData(id: Long) = viewModelScope.launch {
        contactsRepository.getContact(id)
            .onSuccess { setState { copy(contact = success(it), contactForm = it.toForm()) } }
    }

    fun updateContact(contactId: Long) = backgroundScope.launch {
        val contact = currentState.contactForm.toContact().copy(id = contactId)

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

    fun updatePhotoPath(filePath: String) = setState {
        copy(contactForm = contactForm.copy(filePath = filePath))
    }

    fun updateEmail(email: String) = setState {
        copy(contactForm = contactForm.copy(email = email))
    }

    fun updateName(name: String) = setState {
        copy(contactForm = contactForm.copy(name = name))
    }

    fun updatePhone(mobile: String) = setState {
        copy(contactForm = contactForm.copy(mobile = mobile))
    }

    fun resetData() = setState {
        val currentContact = contact() ?: return@setState this
        copy(contactForm = currentContact.toForm())
    }

    fun togglePriority() = setState {
        copy(contactForm = contactForm.copy(priority = contactForm.priority.not()))
    }

    fun sendSms(contactId: Long) = backgroundScope.launch {
        val number = async { contactsRepository.getContact(contactId).map { it.mobile } }
        val geoLocation = async { locationProvider.getLastKnownLocation() }

        zip(number.await(), geoLocation.await())
            .onSuccess {
                textMessageService.sendMessage(it.first, it.second)
            }
    }

    fun callContact(contactId: Long) = backgroundScope.launch {
        contactsRepository.getContact(contactId).onSuccess {
            callService.call(it)
        }
    }
}
