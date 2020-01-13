package com.example.bpawlowski.falldetector.screens.main.alarm

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.service.AlarmService
import com.bpawlowski.domain.service.LocationProvider
import com.bpawlowski.domain.zip
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.StateValue.Loading
import com.example.bpawlowski.falldetector.domain.failure
import com.example.bpawlowski.falldetector.domain.success
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val locationProvider: LocationProvider,
    private val contactRepository: com.bpawlowski.domain.repository.ContactRepository,
    private val alarmService: AlarmService,
    initialState: AlarmViewState = AlarmViewState()
) : BaseViewModel<AlarmViewState>(initialState) {

    fun raiseAlarm() = viewModelScope.launch {

        setState { copy(alarmRequest = Loading) }

        val contacts = async { contactRepository.getAllContacts() }
        val location = async { locationProvider.getLastKnownLocation() }

        zip(contacts.await(), location.await())
            .onSuccess {
                setState { copy(alarmRequest = success(Unit)) }
                alarmService.raiseAlarm(it.first, it.second)
            }.onFailure {
                setState { copy(alarmRequest = failure(it)) }
            }
    }
}
