package com.example.bpawlowski.falldetector.domain

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.bpawlowski.falldetector.screens.preference.model.AppPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

private const val DARK_MODE = "Dark Mode"
private const val SEND_LOCATION = "Send Location"
private const val SEND_SMS = "Send SMS"
private const val SENSITIVITY = "Sensitivity"

class AppPreferenceDelegate(
    private val sharedPreferences: SharedPreferences
) {
    private var preferences = initAppPreferences()

    val preferenceFlow = callbackFlow {
        offer(preferences)

        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                val currentPreferences = when (key) {
                    DARK_MODE -> preferences.copy(
                        darkMode = sharedPreferences.getBoolean(
                            key,
                            false
                        )
                    )
                    SEND_SMS -> preferences.copy(
                        sendingSms = sharedPreferences.getBoolean(
                            key,
                            false
                        )
                    )
                    SEND_LOCATION -> preferences.copy(
                        sendingLocation = sharedPreferences.getBoolean(key, false)
                    )
                    SENSITIVITY -> preferences.copy(
                        sensitivity = sharedPreferences.getString(key, "LOW") ?: "LOW"
                    )
                    else -> preferences
                }

                preferences = currentPreferences
                offer(currentPreferences)
            }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    fun updateBooleanValue(key: String, value: Boolean) =
        sharedPreferences.edit(true) { putBoolean(key, value) }

    fun updateStringValue(key: String, value: String) =
        sharedPreferences.edit(true) { putString(key, value) }

    private fun initAppPreferences() = AppPreferences(
        darkMode = sharedPreferences.getBoolean(DARK_MODE, false),
        sendingSms = sharedPreferences.getBoolean(SEND_SMS, false),
        sendingLocation = sharedPreferences.getBoolean(SEND_LOCATION, false),
        sensitivity = sharedPreferences.getString(SENSITIVITY, "LOW") ?: "LOW"
    )
}
