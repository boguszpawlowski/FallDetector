package com.example.bpawlowski.falldetector

import android.app.Activity
import android.app.Application
import com.example.bpawlowski.falldetector.di.component.DaggerIAppComponent
import com.example.bpawlowski.falldetector.util.initializeStetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class FallDetectorApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        DaggerIAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        initializeStetho(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}