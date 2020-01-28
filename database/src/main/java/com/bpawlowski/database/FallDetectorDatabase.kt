package com.bpawlowski.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dao.ServiceStateDao
import com.bpawlowski.database.entity.ContactDb
import com.bpawlowski.database.entity.SensitivityConverter
import com.bpawlowski.database.entity.ServiceStateDb

@Database(
    entities = [ContactDb::class, ServiceStateDb::class],
    version = 1
)
@TypeConverters(SensitivityConverter::class)
internal abstract class FallDetectorDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    abstract fun serviceStateDao(): ServiceStateDao
}
