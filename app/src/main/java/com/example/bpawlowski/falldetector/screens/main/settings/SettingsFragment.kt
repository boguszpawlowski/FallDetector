package com.example.bpawlowski.falldetector.screens.main.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.bpawlowski.falldetector.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.screen_preferences)
    }
}
