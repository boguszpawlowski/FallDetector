package bogusz.com.service.di

import bogusz.com.service.location.LocationProvider
import bogusz.com.service.location.LocationProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LocationProviderModule {

    @Binds
    @AppScope
    internal abstract fun bindLocationProvider(locationProvider: LocationProviderImpl): LocationProvider
}