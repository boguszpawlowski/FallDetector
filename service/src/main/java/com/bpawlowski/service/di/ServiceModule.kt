package com.bpawlowski.service.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.telephony.SmsManager
import com.bpawlowski.service.alarm.AlarmService
import com.bpawlowski.service.alarm.AlarmServiceImpl
import com.bpawlowski.service.connectivity.CallService
import com.bpawlowski.service.connectivity.CallServiceImpl
import com.bpawlowski.service.connectivity.TextMessageService
import com.bpawlowski.service.connectivity.TextMessageServiceImpl
import com.bpawlowski.service.database.dbservice.DatabaseService
import com.bpawlowski.service.database.dbservice.DatabaseServiceImpl
import com.bpawlowski.service.database.repository.ContactRepository
import com.bpawlowski.service.database.repository.ContactRepositoryImpl
import com.bpawlowski.service.database.repository.ServiceStateRepository
import com.bpawlowski.service.database.repository.ServiceStateRepositoryImpl
import com.bpawlowski.service.location.LocationProvider
import com.bpawlowski.service.location.LocationProviderImpl
import com.bpawlowski.service.rx.SchedulerProvider
import com.bpawlowski.service.rx.SchedulerProviderImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    single<AlarmService> { AlarmServiceImpl(get(), get(), get()) }
    single<SmsManager> { SmsManager.getDefault() }
    single<TextMessageService> { TextMessageServiceImpl(get(), get()) }
    single<CallService> { CallServiceImpl() }
    factory<SharedPreferences>(named("Default")) { PreferenceManager.getDefaultSharedPreferences(get()) }
    single<LocationProvider> { LocationProviderImpl(get()) }
    single<DatabaseService> { DatabaseServiceImpl(get()) }
    single<ContactRepository> { ContactRepositoryImpl(get()) }
    single<ServiceStateRepository> { ServiceStateRepositoryImpl(get()) }
    single<SchedulerProvider> { SchedulerProviderImpl() }
}