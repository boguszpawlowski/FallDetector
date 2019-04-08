package bogusz.com.service.di

import bogusz.com.service.rx.ISchedulerProvider
import bogusz.com.service.rx.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class RxModule {
    @Binds
    @AppScope
    abstract fun bindSchedulerProvider(schedulerProvider: SchedulerProvider): ISchedulerProvider
}