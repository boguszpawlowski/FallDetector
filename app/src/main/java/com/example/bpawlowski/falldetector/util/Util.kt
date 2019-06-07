package com.example.bpawlowski.falldetector.util

import android.content.Context
import android.widget.Toast
import com.example.bpawlowski.falldetector.ui.alarm.AlarmActivity
import com.example.bpawlowski.falldetector.ui.main.call.CallFragment
import com.example.bpawlowski.falldetector.ui.main.contacts.ContactsFragment
import com.example.bpawlowski.falldetector.ui.main.home.HomeFragment
import com.example.bpawlowski.falldetector.ui.main.settings.SettingsFragment
import com.example.bpawlowski.falldetector.ui.main.sms.MessageFragment

val doNothing = Unit

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, message, length).show()

val screenTitles = mapOf(
    HomeFragment::class.java to "Home",
    ContactsFragment::class.java to "Contacts",
    SettingsFragment::class.java to "Settings",
    AlarmActivity::class.java to "Alarm",
    CallFragment::class.java to "Dial",
    MessageFragment::class.java to "Message"
)