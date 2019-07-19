package com.example.bpawlowski.falldetector.ui.main.contacts

import android.content.Context
import androidx.lifecycle.LiveData
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.connectivity.SmsService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactsViewModel(
	private val contactsRepository: ContactRepository,
	private val smsService: SmsService,
	private val callService: CallService,
	private val locationProvider: LocationProvider
) : BaseViewModel() {

	val contactsLiveData: LiveData<List<Contact>>
		get() = contactsRepository.getAllContactsData()

	fun removeContact(contact: Contact) = backgroundScope.launch {
		contactsRepository.removeContact(contact)
			.onFailure { Timber.e(it) }
	}

	fun callContact(context: Context, contact: Contact) = callService.call(context, contact)

	fun sendMessage(contact: Contact) = backgroundScope.launch {
		locationProvider.getLastKnownLocation().onSuccess {
			smsService.sendMessage(contact.mobile, it)
		}
	}
}