package com.bpawlowski.system.di

import android.telephony.SmsManager
import com.bpawlowski.domain.service.AlarmService
import com.bpawlowski.domain.service.CallService
import com.bpawlowski.domain.service.LocationProvider
import com.bpawlowski.domain.service.TextMessageService
import com.bpawlowski.system.alarm.AlarmServiceImpl
import com.bpawlowski.system.connectivity.CallServiceImpl
import com.bpawlowski.system.connectivity.TextMessageServiceImpl
import com.bpawlowski.system.location.LocationProviderImpl
import org.koin.dsl.module

val systemModule = module {
    single<AlarmService> { AlarmServiceImpl(get(), get()) }
    single<CallService> { CallServiceImpl(get()) }
    single<TextMessageService> { TextMessageServiceImpl(get()) }
    single<LocationProvider> { LocationProviderImpl(get()) }
    single<SmsManager> { SmsManager.getDefault() }
}
