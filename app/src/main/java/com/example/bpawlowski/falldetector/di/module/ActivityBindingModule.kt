package com.example.bpawlowski.falldetector.di.module

import com.example.bpawlowski.falldetector.ui.alarm.AlarmActivity
import com.example.bpawlowski.falldetector.ui.login.LoginActivity
import com.example.bpawlowski.falldetector.ui.main.MainActivity
import com.example.bpawlowski.falldetector.ui.main.di.MainActivityModule
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