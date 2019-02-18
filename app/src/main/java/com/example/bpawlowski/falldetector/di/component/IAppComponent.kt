package com.example.bpawlowski.falldetector.di.component

import com.example.bpawlowski.falldetector.App
import com.example.bpawlowski.falldetector.di.annotation.AppScope
import com.example.bpawlowski.falldetector.di.module.ActivityBindingModule
import com.example.bpawlowski.falldetector.di.module.AppModule
import com.example.bpawlowski.falldetector.di.module.ContextModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = arrayOf(AppModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class, ContextModule::class))
interface IAppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): IAppComponent
    }
}