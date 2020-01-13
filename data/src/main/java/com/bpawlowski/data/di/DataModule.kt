package com.bpawlowski.data.di

import com.bpawlowski.domain.di.coroutineModule
import com.bpawlowski.data.repository.ContactRepositoryImpl
import com.bpawlowski.data.repository.EventRepositoryImpl
import com.bpawlowski.data.repository.ServiceStateRepositoryImpl
import com.bpawlowski.domain.repository.ContactRepository
import com.bpawlowski.domain.repository.EventRepository
import com.bpawlowski.domain.repository.ServiceStateRepository
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

object DataModule {

    private val modules = listOf(dataModule, coroutineModule)

    private var loaded = false

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

private val dataModule = module {
    single<EventRepository> { EventRepositoryImpl(get(), get()) }
    single<ContactRepository> { ContactRepositoryImpl(get()) }
    single<ServiceStateRepository> { ServiceStateRepositoryImpl(get()) }
}
