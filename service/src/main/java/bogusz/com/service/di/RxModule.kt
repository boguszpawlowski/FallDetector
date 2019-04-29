package bogusz.com.service.di

import bogusz.com.service.rx.SchedulerProvider
import bogusz.com.service.rx.SchedulerProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RxModule {
    @Binds
    @AppScope
    internal abstract fun bindSchedulerProvider(schedulerProvider: SchedulerProviderImpl): SchedulerProvider
}