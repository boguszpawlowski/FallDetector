package com.example.bpawlowski.falldetector.screens.preference.model

data class AppPreferences(
    val darkMode: Boolean,
    val sendingSms: Boolean,
    val sendingLocation: Boolean,
    val sensitivity: String
)
