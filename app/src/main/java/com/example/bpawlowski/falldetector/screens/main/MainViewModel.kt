package com.example.bpawlowski.falldetector.screens.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.repository.ServiceStateRepository
import com.bpawlowski.system.model.AppSettings
import com.bpawlowski.system.preferences.AppSettingsPreferencesData
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.map
import kotlinx.coroutines.launch

class MainViewModel(
    sharedPreferences: SharedPreferences,
    private val serviceStateRepository: ServiceStateRepository,
    initialState: MainViewState = MainViewState()
) : BaseViewModel<MainViewState>(initialState) {

    val appSettingsPreferencesData = AppSettingsPreferencesData(sharedPreferences)

    val darkModeLiveData: LiveData<Boolean> = appSettingsPreferencesData.map { it.darkMode }

    fun initiateServiceState() = viewModelScope.launch {
        serviceStateRepository.initiateState()
    }

    fun changeServiceState(appSettings: AppSettings) = viewModelScope.launch {
        serviceStateRepository.getServiceState().onSuccess { currentState ->
            serviceStateRepository.updateState(
                currentState.copy(
                    sensitivity = appSettings.sensitivity,
                    sendingSms = appSettings.sendingSms,
                    sendingLocation = appSettings.sendingLocation
                )
            )
        }
    }
}
