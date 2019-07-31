package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.view.View
import android.widget.Toast
import bogusz.com.service.util.doNothing
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(this, message, length).show()


fun snackbar(
	source: View,
	message: String,
	length: Int = Snackbar.LENGTH_LONG,
	actionListener: Int,
	listener: (View) -> Unit = { doNothing }
) =
	with(Snackbar.make(source, message, length)) {
		setAction(actionListener, listener)
		show()
	}

fun snackbar(source: View, message: String, length: Int = Snackbar.LENGTH_LONG) =
	Snackbar.make(source, message, length).show() //todo snackbar above navigation