package com.example.bpawlowski.falldetector

import android.app.Application
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.bpawlowski.service.di.serviceModule
import com.bpawlowski.service.preferences.DARK_THEME_KEY
import com.example.bpawlowski.falldetector.di.viewModelModule
import com.example.bpawlowski.falldetector.util.initializeDebugTools
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FallDetectorApp : Application() {

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        val darkMode = sharedPreferences.getBoolean(DARK_THEME_KEY, false)

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