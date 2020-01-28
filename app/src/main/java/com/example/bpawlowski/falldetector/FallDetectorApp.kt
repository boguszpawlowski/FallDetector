package com.example.bpawlowski.falldetector

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.bpawlowski.data.di.DataModule
import com.bpawlowski.database.di.DatabaseModule
import com.bpawlowski.system.preferences.DARK_THEME_KEY
import com.bpawlowski.system.di.systemModule
import com.example.bpawlowski.falldetector.di.viewModelModule
import com.example.bpawlowski.falldetector.util.initializeDebugTools
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class FallDetectorApp : Application() {

    private val sharedPreferences by lazy {
        applicationContext.getSharedPreferences("Preference", Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()

        val darkMode = sharedPreferences.getBoolean(DARK_THEME_KEY, false)

        AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        initializeDebugTools(this)

        startKoin {
            androidLogger()
            androidContext(this@FallDetectorApp)
        }

        DataModule.load()
        DatabaseModule.load()

        loadKoinModules(
            listOf(
                viewModelModule,
                systemModule
            )
        )
    }

    override fun onTerminate() {
        super.onTerminate()

        DataModule.unload()
        DatabaseModule.unload()
    }
}
