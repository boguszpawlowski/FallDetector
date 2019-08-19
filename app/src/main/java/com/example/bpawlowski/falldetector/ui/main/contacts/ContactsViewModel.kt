package com.example.bpawlowski.falldetector.ui.main.contacts

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bpawlowski.system.connectivity.CallService
import com.bpawlowski.system.connectivity.TextMessageService
import com.bpawlowski.database.repository.ContactRepository
import com.bpawlowski.system.location.LocationProvider
import com.bpawlowski.database.entity.Contact
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactsViewModel(
	private val contactsRepository: ContactRepository,
	private val textMessageService: TextMessageService,
	private val callService: CallService,
	private val locationProvider: LocationProvider
) : BaseViewModel() {

	val contactsLiveData: LiveData<List<Contact>>
		get() = contactsRepository.getAllContactsData()

	private val _screenStateData = MutableLiveData<ScreenState<Contact>>()
	val screenStateData = _screenStateData.toSingleEvent()

	fun removeContact(contact: Contact) = backgroundScope.launch {
		contactsRepository.removeContact(contact)
			.onSuccess { _screenStateData.postValue(ScreenState.Success(it)) }
			.onException { Timber.e(it) }
	}

	fun addContact(contact: Contact) = backgroundScope.launch {
		contactsRepository.addContact(contact.copy(id = null))
				.onException { Timber.e(it) }
	}

	fun callContact(context: Context, contact: Contact) = callService.call(context, contact)

	fun sendMessage(contact: Contact) = backgroundScope.launch {
		locationProvider.getLastKnownLocation().onSuccess {
			textMessageService.sendMessage(contact.mobile, it)
		}
	}
}