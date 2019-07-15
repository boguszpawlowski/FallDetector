package bogusz.com.service.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.telephony.SmsManager
import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.alarm.AlarmServiceImpl
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.connectivity.CallServiceImpl
import bogusz.com.service.connectivity.SmsService
import bogusz.com.service.connectivity.SmsServiceImpl
import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.dbservice.DatabaseServiceImpl
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.database.repository.ContactRepositoryImpl
import bogusz.com.service.database.repository.ServiceStateRepository
import bogusz.com.service.database.repository.ServiceStateRepositoryImpl
import bogusz.com.service.location.LocationProvider
import bogusz.com.service.location.LocationProviderImpl
import bogusz.com.service.rx.SchedulerProvider
import bogusz.com.service.rx.SchedulerProviderImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {

    single<AlarmService> { AlarmServiceImpl(get(), get(), get()) }
    single<SmsManager> { SmsManager.getDefault() }
    single<SmsService> { SmsServiceImpl(get()) }
    single<CallService> { CallServiceImpl() }

    factory<SharedPreferences>(named("Default")) { PreferenceManager.getDefaultSharedPreferences(get()) }

    single<LocationProvider> { LocationProviderImpl(get()) }

    single<DatabaseService> { DatabaseServiceImpl(get()) }

    single<ContactRepository> { ContactRepositoryImpl(get()) }
    single<ServiceStateRepository> { ServiceStateRepositoryImpl(get()) }

    single<SchedulerProvider> { SchedulerProviderImpl() }
}