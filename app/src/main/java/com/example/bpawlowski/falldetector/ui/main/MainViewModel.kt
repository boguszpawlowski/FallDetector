package com.example.bpawlowski.falldetector.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import bogusz.com.service.database.repository.ServiceStateRepository
import bogusz.com.service.model.Sensitivity
import bogusz.com.service.preferences.AppSettingsPreferencesData
import com.example.bpawlowski.falldetector.domain.ErrorNotification
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
	private val sharedPreferences: SharedPreferences,
	private val serviceStateRepository: ServiceStateRepository
) : BaseViewModel() {

	val appSettingsPreferencesData: AppSettingsPreferencesData
		get() = AppSettingsPreferencesData(sharedPreferences)

	private val _errorData = MutableLiveData<ErrorNotification>()
	val errorData: LiveData<ErrorNotification>
		get() = _errorData

	fun changeSensitivity(sensitivity: Sensitivity) = viewModelScope.launch {
		serviceStateRepository.updateSensitivity(sensitivity)
	}

	fun handleKnownError(notification: ErrorNotification) = _errorData.postValue(notification)
}
