package com.example.bpawlowski.falldetector.di.component

import bogusz.com.service.di.*
import com.example.bpawlowski.falldetector.FallDetectorApp
import com.example.bpawlowski.falldetector.di.module.AppModule
import com.example.bpawlowski.falldetector.monitoring.BackgroundService
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@bogusz.com.service.di.AppScope
@Component(
    modules = arrayOf(
        AppModule::class,
        ConnectivityServiceModule::class,
        LocationProviderModule::class,
        RepositoryModule::class,
        SharedPreferencesModule::class,
        RxModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class
    )
)
interface AppComponent {

    fun inject(app: FallDetectorApp)

    fun inject(service: BackgroundService)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: FallDetectorApp): Builder

        fun build(): AppComponent
    }
}