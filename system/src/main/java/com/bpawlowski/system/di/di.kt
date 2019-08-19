package com.bpawlowski.system.di

import android.telephony.SmsManager
import com.bpawlowski.system.alarm.AlarmService
import com.bpawlowski.system.alarm.AlarmServiceImpl
import com.bpawlowski.system.connectivity.CallService
import com.bpawlowski.system.connectivity.CallServiceImpl
import com.bpawlowski.system.connectivity.TextMessageService
import com.bpawlowski.system.connectivity.TextMessageServiceImpl
import com.bpawlowski.system.location.LocationProvider
import com.bpawlowski.system.location.LocationProviderImpl
import com.bpawlowski.system.rx.SchedulerProvider
import com.bpawlowski.system.rx.SchedulerProviderImpl
import org.koin.dsl.module

val systemModule = module {
	single<AlarmService> { AlarmServiceImpl(get(), get(), get()) }
	single<CallService> { CallServiceImpl() }
	single<TextMessageService> { TextMessageServiceImpl(get(), get()) }
	single<LocationProvider> { LocationProviderImpl(get()) }
	single<SchedulerProvider> { SchedulerProviderImpl() }
	single<SmsManager> { SmsManager.getDefault() }
}