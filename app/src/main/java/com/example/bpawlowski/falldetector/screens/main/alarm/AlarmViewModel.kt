package com.example.bpawlowski.falldetector.screens.main.alarm

import androidx.lifecycle.viewModelScope
import com.bpawlowski.core.domain.zip
import com.bpawlowski.system.alarm.AlarmService
import com.bpawlowski.database.repository.ContactRepository
import com.bpawlowski.system.location.LocationProvider
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class AlarmViewModel(
	private val locationProvider: LocationProvider,
	private val contactRepository: ContactRepository,
	private val alarmService: AlarmService
) : BaseViewModel() {

    fun raiseAlarm() = viewModelScope.launch {
        val contacts = async { contactRepository.getAllContacts() }
        val location = async { locationProvider.getLastKnownLocation() }

		zip(contacts.await(), location.await())
			.onSuccess { alarmService.raiseAlarm(it.first, it.second) }
			.onException { Timber.e(it) }
    }
}