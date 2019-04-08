package com.example.bpawlowski.falldetector.di.module

import android.content.Context
import com.example.bpawlowski.falldetector.FallDetectorApp
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule{
    @Binds
    abstract fun provideContext(app: FallDetectorApp) : Context
}