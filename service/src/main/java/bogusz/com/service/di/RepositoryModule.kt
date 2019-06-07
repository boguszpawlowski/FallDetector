package bogusz.com.service.di

import bogusz.com.service.database.repository.ContactRepositoryImpl
import bogusz.com.service.database.repository.ContactRepository
import dagger.Binds
import dagger.Module

@Module(includes = [DatabaseServiceModule::class]) //TODO change visibility of database service to service module only
abstract class RepositoryModule {

    @Binds
    @AppScope
    internal abstract fun bindContactRepository(contactRepository: ContactRepositoryImpl): ContactRepository
}