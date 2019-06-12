package bogusz.com.service.preferences

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import bogusz.com.service.model.AppSettings
import bogusz.com.service.model.Sensitivity

class AppSettingsPreferencesData(
    private val sharedPreferences: SharedPreferences
) : LiveData<AppSettings>() {

    private val appSettings = initAppSettings()

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when (key) {
            DARK_MODE_KEY -> appSettings.darkMode = sharedPreferences.getBoolean(key, false)
            SEND_SMS_KEY -> appSettings.sendingSms = sharedPreferences.getBoolean(key, false)
            SEND_LOCATION_KEY -> appSettings.sendingLocation = sharedPreferences.getBoolean(key, false)
            SENSITIVITY_KEY -> appSettings.sensitivity = mapSensitivity(sharedPreferences.getString(key, "0"))
        }
        postValue(appSettings)
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
        darkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false),
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

    companion object {
        private const val DARK_MODE_KEY = "dark_mode"
        private const val SEND_SMS_KEY = "send_sms"
        private const val SEND_LOCATION_KEY = "send_location"
        private const val SENSITIVITY_KEY = "sensitivity"
    }
}