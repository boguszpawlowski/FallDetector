package bogusz.com.service.di

import bogusz.com.service.alarm.AlarmServiceImpl
import bogusz.com.service.alarm.AlarmService
import bogusz.com.service.connectivity.CallServiceImpl
import bogusz.com.service.connectivity.CallService
import bogusz.com.service.connectivity.SmsService
import bogusz.com.service.connectivity.SmsServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ConnectivityServiceModule{

    @Binds
    @AppScope
    internal abstract fun bindAlarmService(alarmService: AlarmServiceImpl): AlarmService

    @Binds
    @AppScope
    internal abstract fun bindSmsService(smsService: SmsServiceImpl): SmsService

    @Binds
    @AppScope
    internal abstract fun bindCallService(callService: CallServiceImpl): CallService
}