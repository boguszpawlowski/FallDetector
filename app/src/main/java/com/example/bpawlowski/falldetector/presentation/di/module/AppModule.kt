package com.example.bpawlowski.falldetector.presentation.di.module

import dagger.Module

@Module(includes = arrayOf(
    ViewModelModule::class,
    ContextModule::class,
    ActivityBindingModule::class))
class AppModule