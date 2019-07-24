package com.example.bpawlowski.falldetector.domain

import android.view.View
import bogusz.com.service.util.doNothing

sealed class Notification(open val source: View, open val message: String) {

	data class ErrorNotification(
		override val source: View,
		override val message: String
	) : Notification(source, message)

	data class InfoNotification(
		override val source: View,
		override val message: String,
		val actionResId: Int? = null,
		val listener: View.OnClickListener = View.OnClickListener { doNothing }
	) : Notification(source, message)
}