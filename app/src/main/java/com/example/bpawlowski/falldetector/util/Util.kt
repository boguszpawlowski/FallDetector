package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.widget.Toast

val doNothing = Unit

fun Context.toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()