package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.view.View
import android.widget.Toast
import bogusz.com.service.util.doNothing
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.domain.Notification
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(this, message, length).show()

fun snackbar(notification: Notification.InfoNotification) {
	snackbar(
		source = notification.source,
		message = notification.message,
		action = notification.actionResId,
		listener = notification.listener
	)
}

fun snackbar(
	source: View,
	message: String,
	length: Int = Snackbar.LENGTH_LONG,
	action: Int? = null,
	listener: View.OnClickListener = View.OnClickListener { doNothing }
) {
	val snackbar = Snackbar.make(source, message, length)
	action?.let {
		snackbar.setAction(it, listener)
	}
	snackbar.show()
}

val drawerItems = setOf(
	R.id.homeFragment,
	R.id.contactsFragment,
	R.id.alarmFragment
)