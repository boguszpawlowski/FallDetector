package com.example.bpawlowski.falldetector

import com.example.bpawlowski.falldetector.di.component.DaggerIAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

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
    }


    companion object {
        lateinit var instance: App private set
    }
}