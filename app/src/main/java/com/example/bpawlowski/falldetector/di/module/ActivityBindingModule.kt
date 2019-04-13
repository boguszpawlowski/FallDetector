package com.example.bpawlowski.falldetector.di.module

import com.example.bpawlowski.falldetector.activity.alarm.AlarmActivity
import com.example.bpawlowski.falldetector.activity.login.LoginActivity
import com.example.bpawlowski.falldetector.activity.main.MainActivity
import com.example.bpawlowski.falldetector.activity.main.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindAlarmActivity(): AlarmActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity
}