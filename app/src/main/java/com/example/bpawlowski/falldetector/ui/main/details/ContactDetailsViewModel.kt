package com.example.bpawlowski.falldetector.ui.main.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.database.exceptions.FallDetectorException
import bogusz.com.service.database.repository.ContactRepository
import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.copyToForm
import com.example.bpawlowski.falldetector.util.mapToContact
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactDetailsViewModel(
	private val contactsRepository: ContactRepository
) : BaseViewModel() {

	val contactForm = ContactFormModel()

	private val _screenStateData = MutableLiveData<ScreenState<Int>>()
	val screenStateData = _screenStateData.toSingleEvent()

	fun initData(id: Long) = viewModelScope.launch {
		contactsRepository.getContact(id)
			.onSuccess { it.copyToForm(contactForm) }
			.onFailure {
				if (it !is FallDetectorException.NoSuchRecordException) Timber.e(it)
			}
	}

	fun updateContact(contactId: Long) = backgroundScope.launch {
		val contact = contactForm.mapToContact().copy(id = contactId)

		_screenStateData.postValue(ScreenState.Loading)
		contactsRepository.updateContact(contact)
			.onSuccess { _screenStateData.postValue(ScreenState.Success(it)) }
			.onKnownFailure { _screenStateData.postValue(ScreenState.Failure(it)) }
	}
}