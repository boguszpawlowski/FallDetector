package com.example.bpawlowski.falldetector.screens.contacts.add

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.repository.ContactRepository
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import kotlinx.coroutines.launch

class AddContactViewModel(
    private val contactsRepository: ContactRepository,
    initialState: AddContactViewState = AddContactViewState()
) : BaseViewModel<AddContactViewState>(initialState) {

    fun addContact(contact: Contact) = viewModelScope.launch {
        setState { copy(saveContactRequest = Loading) }

        contactsRepository.addContact(contact)
            .onSuccess { setState { copy(saveContactRequest = success(Unit)) } }
            .onFailure { setState { copy(saveContactRequest = failure(it)) } }
    }
}
