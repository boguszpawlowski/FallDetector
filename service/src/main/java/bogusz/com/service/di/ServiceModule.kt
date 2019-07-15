package bogusz.com.service.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
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
import org.koin.dsl.bind
import org.koin.dsl.module

val connectivityServiceModule = module {
	single<AlarmService> { AlarmServiceImpl(get(), get(), get()) }
	single<SmsService> { SmsServiceImpl() }
	single<CallService> { CallServiceImpl() }
}

val sharedPreferencesModule = module {
	single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val locationProviderModule = module {
	single<LocationProvider> { LocationProviderImpl(get()) }
}

val databaseServiceModule = module {
	single<DatabaseService> { DatabaseServiceImpl(get()) }
}

val repositoryModule = module {
	single<ContactRepository> { ContactRepositoryImpl(get()) }
	single<ServiceStateRepository> { ServiceStateRepositoryImpl(get()) }
}

val schedulersModule = module {
	single<SchedulerProvider> { SchedulerProviderImpl() }
}