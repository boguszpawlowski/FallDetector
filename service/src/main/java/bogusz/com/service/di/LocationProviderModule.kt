package bogusz.com.service.di

import bogusz.com.service.location.ILocationProvider
import bogusz.com.service.location.LocationProvider
import dagger.Binds
import dagger.Module

@Module
abstract class LocationProviderModule {
    @Binds
    @AppScope
    abstract fun bindLocationProvider(locationProvider: LocationProvider): ILocationProvider
}