package com.example.bpawlowski.falldetector.screens.main.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.data.repository.ContactRepository
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.ScreenResult
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.domain.reduce
import com.example.bpawlowski.falldetector.util.mapToContact
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.launch

class FormDialogViewModel(
	private val contactsRepository: ContactRepository
) : BaseViewModel() {

	private val _screenStateData = MutableLiveData<ScreenState<Long>>()
	val screenStateData = _screenStateData.toSingleEvent()

	val contactForm = ContactFormModel()

	fun tryToAddContact() = viewModelScope.launch {
		val contact = contactForm.mapToContact()

		_screenStateData.reduce(ScreenResult.Loading)
		contactsRepository.addContact(contact)
			.onSuccess { _screenStateData.reduce(ScreenResult.Success(it)) }
			.onFailure { _screenStateData.reduce(ScreenResult.Failure(it)) }
	}
}