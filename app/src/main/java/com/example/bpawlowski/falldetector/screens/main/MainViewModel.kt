package com.example.bpawlowski.falldetector.screens.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpawlowski.core.domain.EventWrapper
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.data.repository.ServiceStateRepository
import com.bpawlowski.database.util.map
import com.bpawlowski.system.model.AppSettings
import com.bpawlowski.system.preferences.AppSettingsPreferencesData
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.util.toSingleEvent
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
	sharedPreferences: SharedPreferences,
	private val serviceStateRepository: ServiceStateRepository
) : BaseViewModel() {

	val appSettingsPreferencesData = AppSettingsPreferencesData(sharedPreferences)

	val darkModeLiveData: LiveData<Boolean> = appSettingsPreferencesData.map { it.darkMode }

	private val _capturedPhotoData = MutableLiveData<EventWrapper<File>>()
	val capturedPhotoData: LiveData<EventWrapper<File>>
		get() = _capturedPhotoData.toSingleEvent()

	fun onPhotoAdded(file: File) = _capturedPhotoData.postValue(EventWrapper(file))

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
