package com.bpawlowski.database.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.bpawlowski.data.datasource.ContactLocalDataSource
import com.bpawlowski.data.datasource.ServiceStateLocalDataSource
import com.bpawlowski.database.datasource.ContactLocalDataSourceImpl
import com.bpawlowski.database.datasource.ServiceSateLocalDataSourceImpl
import com.bpawlowski.database.dbservice.DatabaseService
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DatabaseModule {
    private var loaded = false

    private val modules = listOf(databaseModule, daoModule, dataSourceModule)

    fun load() {
        if (loaded.not()) {
            loadKoinModules(modules)
            loaded = true
        }
    }

    fun unload() {
        if (loaded) {
            unloadKoinModules(modules)
            loaded = false
        }
    }
}

private val databaseModule = module {
    factory<SharedPreferences>(named("Default")) { PreferenceManager.getDefaultSharedPreferences(get()) }
    single { DatabaseService(get()) }
}

private val daoModule = module {
    single { get<DatabaseService>().getContactDao() }
    single { get<DatabaseService>().getServiceStateDao() }
}

private val dataSourceModule = module {
    single<ContactLocalDataSource> { ContactLocalDataSourceImpl(get()) }
    single<ServiceStateLocalDataSource> { ServiceSateLocalDataSourceImpl(get()) }
}
