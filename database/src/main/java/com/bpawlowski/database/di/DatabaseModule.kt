package com.bpawlowski.database.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.bpawlowski.database.dbservice.DatabaseService
import com.bpawlowski.database.dbservice.DatabaseServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {
	factory<SharedPreferences>(named("Default")) { PreferenceManager.getDefaultSharedPreferences(get()) }
	single<DatabaseService> { DatabaseServiceImpl(get()) }
}