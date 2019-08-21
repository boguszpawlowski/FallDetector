package com.example.bpawlowski.falldetector.screens.main.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.database.repository.ContactRepository
import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
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
			.onFailure { _screenStateData.postValue(ScreenState.Failure(it)) }
	}
}