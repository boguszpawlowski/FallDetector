package com.example.bpawlowski.falldetector.ui.main.call

import android.content.Context
import androidx.lifecycle.LiveData
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel

class CallViewModel(
	private val contactRepository: ContactRepository,
	private val callService: CallService
) : BaseViewModel() {

	val contactsLiveData: LiveData<List<Contact>>
		get() = contactRepository.getAllContactsData()

	fun callContact(context: Context, contact: Contact) = callService.call(context, contact)
}