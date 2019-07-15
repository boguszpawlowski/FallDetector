package com.example.bpawlowski.falldetector

import android.app.Application
import bogusz.com.service.di.serviceModule
import com.example.bpawlowski.falldetector.di.viewModelModule
import com.example.bpawlowski.falldetector.util.initializeDebugTools
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FallDetectorApp : Application() {

    override fun onCreate() {
        super.onCreate()

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