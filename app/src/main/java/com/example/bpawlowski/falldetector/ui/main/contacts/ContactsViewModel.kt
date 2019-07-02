package com.example.bpawlowski.falldetector.ui.main.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactRepository
) : BaseViewModel() {

    val contactsLiveData: LiveData<List<Contact>>
        get() = contactsRepository.getAllContactsData()

    fun removeContact(contact: Contact) = viewModelScope.launch {
        contactsRepository.removeContact(contact)
            .onFailure { Timber.e(it) }
    }
}