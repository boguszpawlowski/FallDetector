package com.bpawlowski.system.model

import com.bpawlowski.database.entity.Sensitivity

data class AppSettings(
    val darkMode: Boolean,
    val sendingSms: Boolean,
    val sendingLocation: Boolean,
    val sensitivity: Sensitivity
)