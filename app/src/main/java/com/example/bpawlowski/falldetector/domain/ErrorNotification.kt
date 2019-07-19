package com.example.bpawlowski.falldetector.domain

import android.view.View

data class ErrorNotification(
	val source: View,
	val message: String
)