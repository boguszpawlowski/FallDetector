package com.example.bpawlowski.falldetector.util

import android.app.Application
import com.facebook.stetho.Stetho
import com.melegy.redscreenofdeath.RedScreenOfDeath
import timber.log.Timber

fun initializeDebugTools(application: Application) {
    Stetho.initializeWithDefaults(application)
    Timber.plant(Timber.DebugTree())
    RedScreenOfDeath.init(application)
}
