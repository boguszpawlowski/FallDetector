package com.example.bpawlowski.falldetector.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.bpawlowski.service.database.repository.ServiceStateRepository
import com.bpawlowski.service.model.AppSettings
import com.bpawlowski.service.preferences.AppSettingsPreferencesData
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
	private val sharedPreferences: SharedPreferences,
	private val serviceStateRepository: ServiceStateRepository
) : BaseViewModel() {

	val appSettingsPreferencesData: AppSettingsPreferencesData
		get() = AppSettingsPreferencesData(sharedPreferences)

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
