package com.example.bpawlowski.falldetector.screens.main.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.system.connectivity.CallService
import com.bpawlowski.system.connectivity.TextMessageService
import com.bpawlowski.core.exception.FallDetectorException
import com.bpawlowski.database.repository.ContactRepository
import com.bpawlowski.core.domain.zip
import com.bpawlowski.system.location.LocationProvider
import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.mapToContact
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

class ContactDetailsViewModel(
	private val contactsRepository: ContactRepository,
	private val textMessageService: TextMessageService,
	private val callService: CallService,
	private val locationProvider: LocationProvider
) : BaseViewModel() {

	val contactForm = ContactFormModel()

	private val _screenStateData = MutableLiveData<ScreenState<Unit>>()
	val screenStateData = _screenStateData.toSingleEvent()

	fun initData(id: Long, photoFile: File? = null) = viewModelScope.launch {
		contactsRepository.getContact(id)
			.onSuccess {
				contactForm.initData(it)
				photoFile?.let { contactForm.filePath = it.toURI().toString() }
			}.onException {
				if (it !is FallDetectorException.NoSuchRecordException) Timber.e(it)
			}
	}

	fun updateContact(contactId: Long) = backgroundScope.launch {
		val contact = contactForm.mapToContact().copy(id = contactId)

		_screenStateData.postValue(ScreenState.Loading)
		contactsRepository.updateContact(contact)
			.onSuccess {
				_screenStateData.postValue(ScreenState.Success(Unit))
				initData(contactId)
			}.onFailure {
				_screenStateData.postValue(ScreenState.Failure(it))
			}
	}

	fun updatePhotoPath(file: File){
		contactForm.filePath = file.toURI().toString()
	}

	fun resetData() = contactForm.resetData()

	fun togglePriority() {
		contactForm.priority = !contactForm.priority
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