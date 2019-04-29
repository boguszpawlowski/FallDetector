package com.example.bpawlowski.falldetector.util

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context

val Context.notificationManager: NotificationManager
    get()  = getSystemService(IntentService.NOTIFICATION_SERVICE) as NotificationManager