package com.bpawlowski.system.di

import android.telephony.SmsManager
import com.bpawlowski.domain.service.AlarmService
import com.bpawlowski.domain.service.ConnectivityService
import com.bpawlowski.domain.service.LocationProvider
import com.bpawlowski.system.alarm.AlarmServiceImpl
import com.bpawlowski.system.connectivity.ConnectivityServiceImpl
import com.bpawlowski.system.location.LocationProviderImpl
import org.koin.dsl.module

val systemModule = module {
    single<AlarmService> { AlarmServiceImpl(get()) }
    single<ConnectivityService> { ConnectivityServiceImpl(get(), get()) }
    single<LocationProvider> { LocationProviderImpl(get()) }
    single<SmsManager> { SmsManager.getDefault() }
}
