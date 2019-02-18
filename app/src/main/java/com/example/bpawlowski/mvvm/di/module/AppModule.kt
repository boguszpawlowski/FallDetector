package com.example.bpawlowski.mvvm.di.module

import dagger.Module

@Module(includes = arrayOf(ViewModelModule::class, NetworkModule::class))
class AppModule