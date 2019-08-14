package com.example.bpawlowski.falldetector.screens.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.core.domain.Event
import com.bpawlowski.database.repository.ServiceStateRepository
import com.bpawlowski.system.model.AppSettings
import com.bpawlowski.system.preferences.AppSettingsPreferencesData
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
	private val sharedPreferences: SharedPreferences,
	private val serviceStateRepository: ServiceStateRepository
) : BaseViewModel() {

	val appSettingsPreferencesData: AppSettingsPreferencesData
		get() = AppSettingsPreferencesData(sharedPreferences)

	private val _capturedPhotoData = MutableLiveData<Event<File>>()
	val capturedPhotoData: LiveData<Event<File>>
		get() = _capturedPhotoData.toSingleEvent()

	fun onPhotoAdded(file: File) = _capturedPhotoData.postValue(Event(file))

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
