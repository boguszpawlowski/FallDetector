package bogusz.com.service.di

import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.database.repository.ContactRepositoryImpl
import bogusz.com.service.database.repository.ServiceStateRepository
import bogusz.com.service.database.repository.ServiceStateRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [DatabaseServiceModule::class])
abstract class RepositoryModule {

    @Binds
    @AppScope
    internal abstract fun bindContactRepository(contactRepository: ContactRepositoryImpl): ContactRepository

    @Binds
    @AppScope
    internal abstract fun bindServiceStateRepository(serviceStateRepository: ServiceStateRepositoryImpl): ServiceStateRepository
}