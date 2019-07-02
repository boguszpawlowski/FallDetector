package com.example.bpawlowski.falldetector.ui.main.sms

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.connectivity.SmsService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val locationProvider: LocationProvider,
    private val smsService: SmsService
) : BaseViewModel() {

    val contactsData: LiveData<List<Contact>>
        get() = contactRepository.getAllContactsData()

    fun sendMessage(contact: Contact) = viewModelScope.launch {
        locationProvider.getLastKnownLocation().onSuccess {
            smsService.sendMessage(contact.mobile, it)
        }
    }
}