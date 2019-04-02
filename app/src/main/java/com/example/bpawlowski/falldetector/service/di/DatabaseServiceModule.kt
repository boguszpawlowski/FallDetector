package com.example.bpawlowski.falldetector.service.di

import com.example.bpawlowski.falldetector.presentation.di.annotation.AppScope
import com.example.bpawlowski.falldetector.service.database.dbservice.DatabaseService
import com.example.bpawlowski.falldetector.service.database.dbservice.IDatabaseService
import com.example.bpawlowski.falldetector.service.database.repository.ContactRepository
import com.example.bpawlowski.falldetector.service.database.repository.IContactRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DatabaseServiceModule{

    @Binds
    @AppScope
    abstract fun bindDatabaseService(databaseService: DatabaseService): IDatabaseService

    @Binds
    @AppScope
    abstract fun bindContactRepository(contactRepository: ContactRepository): IContactRepository
}