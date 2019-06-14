package com.example.bpawlowski.falldetector.ui.alarm

import androidx.lifecycle.viewModelScope
import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.database.success
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.rx.SchedulerProvider
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val contactRepository: ContactRepository,
    private val alarmService: AlarmService
) : BaseViewModel() {

    fun raiseAlarm() {
        viewModelScope.launch {
            runCatching {
                alarmService.raiseAlarm(
                    contactRepository.getAllContacts().success,
                    locationProvider.getLastKnownLocation()
                )
            }.onFailure { Timber.e(it) }
        }
    }
}