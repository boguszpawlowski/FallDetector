package com.example.bpawlowski.falldetector.screens.preference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bpawlowski.domain.model.Sensitivity
import com.example.bpawlowski.falldetector.domain.AppPreferenceDelegate
import com.example.bpawlowski.falldetector.screens.preference.compose.PreferenceItem
import com.example.bpawlowski.falldetector.screens.preference.compose.PreferenceOption
import com.example.bpawlowski.falldetector.screens.preference.model.AppPreferences
import kotlinx.coroutines.flow.*

private const val DARK_MODE = "Dark Mode"
private const val SEND_LOCATION = "Send Location"
private const val SEND_SMS = "Send SMS"
private const val SENSITIVITY = "Sensitivity"

class PreferenceViewModel(
    private val appPreferenceDelegate: AppPreferenceDelegate
) : ViewModel() {

    private val _preferenceFlow = MutableStateFlow<List<PreferenceItem>>(emptyList())
    val preferenceFlow: StateFlow<List<PreferenceItem>> = _preferenceFlow

    init {
        appPreferenceDelegate.preferenceFlow.onEach { appPreferences ->
            _preferenceFlow.value = mapToUiItems(appPreferences)
        }.launchIn(viewModelScope)
    }

    val updateBooleanValue: (String, Boolean) -> Unit = appPreferenceDelegate::updateBooleanValue

    val updateStringValue: (String, String) -> Unit = appPreferenceDelegate::updateStringValue

    private fun mapToUiItems(
        preferences: AppPreferences
    ) = with(preferences) {
        listOf(
            PreferenceItem.Header("Visuals"),
            PreferenceItem.BooleanPreference(DARK_MODE, darkMode),
            PreferenceItem.BooleanPreference(SEND_LOCATION, sendingLocation),
            PreferenceItem.Header("Functional"),
            PreferenceItem.BooleanPreference(SEND_SMS, sendingSms),
            PreferenceItem.ListPreference(
                Sensitivity::class.java.simpleName,
                Sensitivity.values().toList()
                    .map { PreferenceOption(it.name, it.name == sensitivity) }
            )
        )
    }
}
