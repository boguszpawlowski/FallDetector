package com.example.bpawlowski.falldetector.util

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

fun initializeDebugTools(app: Application) {
    Stetho.initializeWithDefaults(app)
    Timber.plant(Timber.DebugTree())
}