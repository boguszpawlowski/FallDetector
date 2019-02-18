package com.example.bpawlowski.mvvm.di.module

import android.content.Context
import com.example.bpawlowski.mvvm.App
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule{
    @Binds
    abstract fun provideContext(app: App) : Context
}