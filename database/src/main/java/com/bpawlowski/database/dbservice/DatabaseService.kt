package com.bpawlowski.database.dbservice

import android.content.Context
import androidx.room.Room
import com.bpawlowski.database.FallDetectorDatabase
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dao.EventDao
import com.bpawlowski.database.dao.ServiceStateDao

private const val name = "FDDatabase"

internal class DatabaseService(
    context: Context
) {

    private var dbInstance: FallDetectorDatabase

    init {
        dbInstance = Room.databaseBuilder(
            context,
            FallDetectorDatabase::class.java,
            name
        ).fallbackToDestructiveMigration()
            .build()
    }

    fun getContactDao(): ContactDao = dbInstance.contactDao()

    fun getServiceStateDao(): ServiceStateDao = dbInstance.serviceStateDao()

    fun getEventDao(): EventDao = dbInstance.eventDao()
}
