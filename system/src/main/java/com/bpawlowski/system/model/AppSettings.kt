package com.bpawlowski.system.model

import com.bpawlowski.core.model.Sensitivity

data class AppSettings(
	val darkMode: Boolean,
	val sendingSms: Boolean,
	val sendingLocation: Boolean,
	val sensitivity: Sensitivity
)