package com.example.bpawlowski.falldetector.ui.main.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.database.exceptions.FallDetectorException
import bogusz.com.service.database.onFailure
import bogusz.com.service.database.onSuccess
import bogusz.com.service.database.repository.ContactRepository
import com.example.bpawlowski.falldetector.domain.ContactForm
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.copyToForm
import com.example.bpawlowski.falldetector.util.mapToContact
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FormDialogViewModel @Inject constructor(
    private val contactsRepository: ContactRepository
) : BaseViewModel() {

    private val _addContactResultData = MutableLiveData<Boolean>()
    val addContactResultData: LiveData<Boolean>
        get() = _addContactResultData

    val contactForm = ContactForm()

    fun initData(id: Long?) = id?.let {
        viewModelScope.launch {
            contactsRepository.getContact(id)
                .onSuccess { it.copyToForm(contactForm) }
                .onFailure {
                    if (it !is FallDetectorException.NoSuchRecordException) Timber.e(it)
                }
        }
    }

    fun tryToAddContact(contactId: Long? = null) = viewModelScope.launch {
        val contact = contactForm.mapToContact().apply { id = contactId }

        contactsRepository.addContact(contact)
            .onSuccess { _addContactResultData.postValue(true) }
            .onFailure {
                Timber.e(it)
                _addContactResultData.postValue(false)
            }
    }
}