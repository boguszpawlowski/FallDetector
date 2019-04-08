package bogusz.com.service.di

import bogusz.com.service.connectivity.CallService
import bogusz.com.service.connectivity.ICallService
import bogusz.com.service.connectivity.ISmsService
import bogusz.com.service.connectivity.SmsService
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ConnectivityServiceModule{

    @Binds
    @Singleton
    abstract fun bindSmsService(smsService: SmsService): ISmsService

    @Binds
    @Singleton
    abstract fun bindCallService(callService: CallService): ICallService
}