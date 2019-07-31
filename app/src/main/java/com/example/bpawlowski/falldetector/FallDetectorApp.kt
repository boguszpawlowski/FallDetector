package com.example.bpawlowski.falldetector

import android.app.Application
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import bogusz.com.service.di.serviceModule
import com.example.bpawlowski.falldetector.di.viewModelModule
import com.example.bpawlowski.falldetector.util.initializeDebugTools
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

private const val DARK_MODE_KEY = "dark_theme"

class FallDetectorApp : Application() {

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        val darkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false)

        AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        initializeDebugTools(this)

        startKoin {
            androidLogger()
            androidContext(this@FallDetectorApp)
            modules(
                listOf(viewModelModule, serviceModule)
            )
        }
    }
}