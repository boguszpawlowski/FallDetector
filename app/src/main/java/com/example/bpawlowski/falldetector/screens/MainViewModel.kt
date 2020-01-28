package com.example.bpawlowski.falldetector.screens

import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.model.Sensitivity
import com.bpawlowski.domain.repository.ServiceStateRepository
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.AppPreferenceDelegate
import com.example.bpawlowski.falldetector.screens.preference.model.AppPreferences
import kotlinx.coroutines.launch

class MainViewModel(
    preferenceDelegate: AppPreferenceDelegate,
    private val serviceStateRepository: ServiceStateRepository,
    initialState: MainViewState = MainViewState()
) : BaseViewModel<MainViewState>(initialState) {

    val appSettingsFlow = preferenceDelegate.preferenceFlow

    fun initiateServiceState() = viewModelScope.launch {
        serviceStateRepository.initiateState()
    }

    fun changeServiceState(appPreferences: AppPreferences) = viewModelScope.launch {
        serviceStateRepository.getServiceState().onSuccess { currentState ->
            serviceStateRepository.updateState(
                currentState.copy(
                    sensitivity = Sensitivity.valueOf(appPreferences.sensitivity),
                    sendingSms = appPreferences.sendingSms,
                    sendingLocation = appPreferences.sendingLocation
                )
            )
        }
    }
}
