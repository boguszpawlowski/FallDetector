package com.bpawlowski.data.di

import com.bpawlowski.core.di.coroutineModule
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.data.repository.EventRepositoryImpl
import com.bpawlowski.database.di.databaseModule
import com.bpawlowski.remote.di.remoteModule
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val dataModule = module {
    single<EventRepository> { EventRepositoryImpl(get(), get()) }
}

val dataKoinInstance = koinApplication { //todo check how to implement this
    modules(
        listOf(
            remoteModule,
            databaseModule,
            coroutineModule
        )
    )
}