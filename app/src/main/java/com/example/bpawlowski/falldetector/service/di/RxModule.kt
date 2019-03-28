package com.example.bpawlowski.falldetector.service.di

import com.example.bpawlowski.falldetector.presentation.di.annotation.AppScope
import com.example.bpawlowski.falldetector.service.rx.ISchedulerProvider
import com.example.bpawlowski.falldetector.service.rx.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class RxModule {
    @Binds
    @AppScope
    abstract fun bindSchedulerProvider(schedulerProvider: SchedulerProvider): ISchedulerProvider
}