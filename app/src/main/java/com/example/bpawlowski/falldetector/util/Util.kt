package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.widget.Toast

val doNothing = Unit

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, message, length).show()