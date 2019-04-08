package bogusz.com.service.di

import bogusz.com.service.database.dbservice.DatabaseService
import bogusz.com.service.database.dbservice.IDatabaseService
import bogusz.com.service.database.repository.ContactRepository
import bogusz.com.service.database.repository.IContactRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DatabaseServiceModule {

    @Binds
    @AppScope
    abstract fun bindDatabaseService(databaseService: DatabaseService): IDatabaseService

    @Binds
    @AppScope
    abstract fun bindContactRepository(contactRepository: ContactRepository): IContactRepository
}