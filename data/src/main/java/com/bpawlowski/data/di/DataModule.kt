package com.bpawlowski.data.di

import com.bpawlowski.core.di.coroutineModule
import com.bpawlowski.data.repository.ContactRepository
import com.bpawlowski.data.repository.ContactRepositoryImpl
import com.bpawlowski.data.repository.EventRepository
import com.bpawlowski.data.repository.EventRepositoryImpl
import com.bpawlowski.data.repository.ServiceStateRepository
import com.bpawlowski.data.repository.ServiceStateRepositoryImpl
import com.bpawlowski.database.di.databaseModule
import com.bpawlowski.remote.di.remoteModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val dataModule = module {
	single<EventRepository> { EventRepositoryImpl(get(), get()) }
	single<ContactRepository> { ContactRepositoryImpl(get()) }
	single<ServiceStateRepository> { ServiceStateRepositoryImpl(get()) }
}

object DataModule {

	private var loaded = false

	fun load() {
		if(loaded.not()) {
			loadKoinModules(
				listOf(
					remoteModule,
					databaseModule,
					coroutineModule
				)
			)
			loaded = true
		}
	}

	fun unload() {
		if(loaded) {
			unloadKoinModules(
				listOf(
					remoteModule,
					databaseModule,
					coroutineModule
				)
			)
			loaded = false
		}
	}
}