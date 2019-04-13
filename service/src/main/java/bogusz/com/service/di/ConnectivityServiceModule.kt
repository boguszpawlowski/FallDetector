package bogusz.com.service.di

import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.alarm.IAlarmService
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
    @AppScope
    abstract fun bindAlarmService(alarmService: AlarmService): IAlarmService

    @Binds
    @AppScope
    abstract fun bindSmsService(smsService: SmsService): ISmsService

    @Binds
    @AppScope
    abstract fun bindCallService(callService: CallService): ICallService
}