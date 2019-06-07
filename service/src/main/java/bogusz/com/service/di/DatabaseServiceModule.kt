package bogusz.com.service.di

import bogusz.com.service.database.dbservice.DatabaseServiceImpl
import bogusz.com.service.database.dbservice.DatabaseService
import dagger.Binds
import dagger.Module

@Module
internal abstract class DatabaseServiceModule {

    @Binds
    @AppScope
    abstract fun bindDatabaseService(databaseService: DatabaseServiceImpl): DatabaseService
}