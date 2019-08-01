package com.example.bpawlowski.falldetector.ui.main.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.connectivity.TextMessageService
import bogusz.com.service.database.exceptions.FallDetectorException
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.database.zip
import bogusz.com.service.location.LocationProvider
import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.mapToContact
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class ContactDetailsViewModel(
	private val contactsRepository: ContactRepository,
	private val textMessageService: TextMessageService,
	private val callService: CallService,
	private val locationProvider: LocationProvider
) : BaseViewModel() {

	val contactForm = ContactFormModel()

	private val _screenStateData = MutableLiveData<ScreenState<Int>>()
	val screenStateData = _screenStateData.toSingleEvent()

	fun initData(id: Long) = viewModelScope.launch {
		contactsRepository.getContact(id)
			.onSuccess {
				contactForm.initData(it)
			}.onFailure {
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

	fun sendSms(contactId: Long) = backgroundScope.launch {

		val number = async { contactsRepository.getContact(contactId).map { it.mobile } }
		val geoLocation = async { locationProvider.getLastKnownLocation() }

		zip(number.await(), geoLocation.await())
			.onSuccess {
				textMessageService.sendMessage(it.first, it.second)
			}
	}

	fun callContact(contactId: Long, context: Context) = backgroundScope.launch {
		contactsRepository.getContact(contactId).onSuccess {
			callService.call(context, it)
		}
	}
}