package com.example.bpawlowski.falldetector.presentation.di.component

import com.example.bpawlowski.falldetector.FallDetectorApp
import com.example.bpawlowski.falldetector.presentation.di.annotation.AppScope
import com.example.bpawlowski.falldetector.presentation.di.module.AppModule
import com.example.bpawlowski.falldetector.service.di.ConnectivityServiceModule
import com.example.bpawlowski.falldetector.service.di.DatabaseServiceModule
import com.example.bpawlowski.falldetector.service.di.RxModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = arrayOf(
    AppModule::class,
    ConnectivityServiceModule::class,
    DatabaseServiceModule::class,
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