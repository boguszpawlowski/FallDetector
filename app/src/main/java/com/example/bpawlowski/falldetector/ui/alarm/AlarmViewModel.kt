package com.example.bpawlowski.falldetector.ui.alarm

import androidx.lifecycle.viewModelScope
import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.database.onFailure
import bogusz.com.service.database.onSuccess
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.database.zip
import bogusz.com.service.location.LocationProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val contactRepository: ContactRepository,
    private val alarmService: AlarmService
) : BaseViewModel() {

    fun raiseAlarm() = viewModelScope.launch {
        zip(contactRepository.getAllContacts(), locationProvider.getLastKnownLocation()) { first, second ->
            first to second
        }.onSuccess { alarmService.raiseAlarm(it.first, it.second) }
            .onFailure { Timber.e(it) }
    }
}