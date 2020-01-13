package com.example.bpawlowski.falldetector.screens.main.contacts

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.repository.ContactRepository
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import com.example.bpawlowski.falldetector.domain.toContact
import kotlinx.coroutines.launch

class AddContactViewModel(
    private val contactsRepository: ContactRepository,
    initialState: AddContactViewState = AddContactViewState()
) : BaseViewModel<AddContactViewState>(initialState) {

    fun updatePhotoPath(file: String) = setState {
        copy(contactForm = contactForm.copy(filePath = file))
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

    fun tryToAddContact() = viewModelScope.launch {
        setState { copy(saveContactRequest = Loading) }

        contactsRepository.addContact(currentState.contactForm.toContact())
            .onSuccess { setState { copy(saveContactRequest = success(Unit)) } }
            .onFailure { setState { copy(saveContactRequest = failure(it)) } }
    }
}
