package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.widget.Toast
import com.example.bpawlowski.falldetector.R

val doNothing = Unit

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, message, length).show()

val drawerItems = setOf(
    R.id.homeFragment,
    R.id.contactsFragment,
    R.id.alarmFragment,
    R.id.callFragment,
    R.id.messageFragment
)