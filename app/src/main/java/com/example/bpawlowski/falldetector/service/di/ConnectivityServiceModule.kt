package com.example.bpawlowski.falldetector.service.di

import com.example.bpawlowski.falldetector.service.connectivity.CallService
import com.example.bpawlowski.falldetector.service.connectivity.ICallService
import com.example.bpawlowski.falldetector.service.connectivity.ISmsService
import com.example.bpawlowski.falldetector.service.connectivity.SmsService
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ConnectivityServiceModule{

    @Binds
    @Singleton
    abstract fun bindSmsService(smsService: SmsService): ISmsService

    @Binds
    @Singleton
    abstract fun bindCallService(callService: CallService): ICallService
}