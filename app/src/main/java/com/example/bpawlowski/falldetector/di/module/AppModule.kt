package com.example.bpawlowski.falldetector.di.module

import dagger.Module

@Module(includes = [ContextModule::class, ActivityBindingModule::class, ViewModelModule::class])
class AppModule