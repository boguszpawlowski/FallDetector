package com.bpawlowski.database.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.bpawlowski.database.dbservice.DatabaseService
import com.bpawlowski.database.dbservice.DatabaseServiceImpl
import com.bpawlowski.database.repository.ContactRepository
import com.bpawlowski.database.repository.ContactRepositoryImpl
import com.bpawlowski.database.repository.ServiceStateRepository
import com.bpawlowski.database.repository.ServiceStateRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
	factory<SharedPreferences>(named("Default")) { PreferenceManager.getDefaultSharedPreferences(get()) }
	single<DatabaseService> { DatabaseServiceImpl(get()) }
	single<ContactRepository> { ContactRepositoryImpl(get()) }
	single<ServiceStateRepository> { ServiceStateRepositoryImpl(get()) }
}