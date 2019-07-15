package com.example.bpawlowski.falldetector

import android.app.Application
import bogusz.com.service.di.connectivityServiceModule
import bogusz.com.service.di.databaseServiceModule
import bogusz.com.service.di.locationProviderModule
import bogusz.com.service.di.repositoryModule
import bogusz.com.service.di.schedulersModule
import bogusz.com.service.di.sharedPreferencesModule
import com.example.bpawlowski.falldetector.di.viewModelModule
import com.example.bpawlowski.falldetector.util.initializeDebugTools
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FallDetectorApp : Application() {

	//    @Inject
	//    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

	override fun onCreate() {
		super.onCreate()

		initializeDebugTools(this)

		startKoin {
			androidLogger()
			androidContext(this@FallDetectorApp)
			modules(
				listOf(
					viewModelModule,
					connectivityServiceModule,
					sharedPreferencesModule,
					locationProviderModule,
					repositoryModule,
					databaseServiceModule,
					schedulersModule
				)
			)
		}
	}
}