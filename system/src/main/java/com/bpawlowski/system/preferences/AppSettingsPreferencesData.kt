package com.bpawlowski.system.preferences

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.bpawlowski.system.model.AppSettings
import com.bpawlowski.database.entity.Sensitivity

const val DARK_THEME_KEY = "dark_theme"
private const val SEND_SMS_KEY = "send_sms"
private const val SEND_LOCATION_KEY = "send_location"
private const val SENSITIVITY_KEY = "sensitivity"

class AppSettingsPreferencesData(
	private val sharedPreferences: SharedPreferences
) : LiveData<AppSettings>() {

	private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
		val temp = value ?: initAppSettings()
		postValue(
			when (key) {
				DARK_THEME_KEY -> temp.copy(darkMode = sharedPreferences.getBoolean(key, false))
				SEND_SMS_KEY -> temp.copy(sendingSms = sharedPreferences.getBoolean(key, false))
				SEND_LOCATION_KEY -> temp.copy(sendingLocation = sharedPreferences.getBoolean(key, false))
				SENSITIVITY_KEY -> temp.copy(sensitivity = mapSensitivity(sharedPreferences.getString(key, "0")))
				else -> temp
			}
		)
	}

	override fun onActive() {
		super.onActive()

		sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
	}

	override fun onInactive() {
		super.onInactive()

		sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
	}

	private fun initAppSettings() = AppSettings(
		darkMode = sharedPreferences.getBoolean(DARK_THEME_KEY, false),
		sendingSms = sharedPreferences.getBoolean(SEND_SMS_KEY, false),
		sendingLocation = sharedPreferences.getBoolean(SEND_LOCATION_KEY, false),
		sensitivity = mapSensitivity(sharedPreferences.getString(SENSITIVITY_KEY, "0"))
	)

	private fun mapSensitivity(sensitivity: String?): Sensitivity = when (sensitivity) {
		"0" -> Sensitivity.LOW
		"1" -> Sensitivity.MEDIUM
		"2" -> Sensitivity.HIGH
		else -> Sensitivity.LOW
	}
}