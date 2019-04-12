package com.example.bpawlowski.falldetector.di.component

import com.example.bpawlowski.falldetector.FallDetectorApp
import com.example.bpawlowski.falldetector.di.module.AppModule
import bogusz.com.service.di.ConnectivityServiceModule
import bogusz.com.service.di.DatabaseServiceModule
import bogusz.com.service.di.LocationProviderModule
import bogusz.com.service.di.RxModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@bogusz.com.service.di.AppScope
@Component(modules = arrayOf(
    AppModule::class,
    ConnectivityServiceModule::class,
    DatabaseServiceModule::class,
    LocationProviderModule::class,
    RxModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class))
interface IAppComponent {

    fun inject(app: FallDetectorApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: FallDetectorApp): Builder

        fun build(): IAppComponent
    }
}