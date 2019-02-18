package com.example.bpawlowski.falldetector.presentation.di.module

import com.example.bpawlowski.falldetector.presentation.activity.login.LoginActivity
import com.example.bpawlowski.falldetector.presentation.activity.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity
}