package com.example.bpawlowski.falldetector

import com.example.bpawlowski.falldetector.presentation.di.component.DaggerIAppComponent
import com.example.bpawlowski.falldetector.presentation.util.initializeStetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class FallDetectorApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerIAppComponent.builder()
            .application(this)
            .build()
        component.inject(this)

        return component
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //for database debugging
        initializeStetho(this)
    }


    companion object {
        lateinit var instance: FallDetectorApp private set
    }
}