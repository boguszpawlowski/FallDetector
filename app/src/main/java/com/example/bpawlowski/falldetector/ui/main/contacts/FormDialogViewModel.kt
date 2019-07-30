package com.example.bpawlowski.falldetector.ui.main.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.database.repository.ContactRepository
import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.mapToContact
import kotlinx.coroutines.launch

class FormDialogViewModel(
	private val contactsRepository: ContactRepository
) : BaseViewModel() {

	private val _screenStateData = MutableLiveData<ScreenState<Long>>()
	val screenStateData: LiveData<ScreenState<Long>>
		get() = _screenStateData

	val contactForm = ContactFormModel()

	fun tryToAddContact() = viewModelScope.launch {
		val contact = contactForm.mapToContact()

		_screenStateData.postValue(ScreenState.Loading)
		contactsRepository.addContact(contact)
			.onSuccess { _screenStateData.postValue(ScreenState.Success(it)) }
			.onKnownFailure { _screenStateData.postValue(ScreenState.Failure(it)) }
	}
}