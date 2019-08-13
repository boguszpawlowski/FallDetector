package com.bpawlowski.service.model

data class AppSettings(
    val darkMode: Boolean,
    val sendingSms: Boolean,
    val sendingLocation: Boolean,
    val sensitivity: Sensitivity
)