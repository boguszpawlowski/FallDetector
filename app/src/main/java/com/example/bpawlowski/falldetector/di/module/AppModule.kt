package com.example.bpawlowski.falldetector.di.module

import dagger.Module

@Module(includes = arrayOf(ViewModelModule::class, NetworkModule::class))
class AppModule