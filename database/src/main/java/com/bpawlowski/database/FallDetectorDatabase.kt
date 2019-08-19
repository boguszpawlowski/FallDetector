package com.bpawlowski.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dao.ServiceStateDao
import com.bpawlowski.database.entity.Contact
import com.bpawlowski.database.entity.SensitivityConverter
import com.bpawlowski.database.entity.ServiceState
import com.bpawlowski.database.entity.UserPriorityConverter

@Database(
    entities = arrayOf(
        Contact::class,
        ServiceState::class
    ),
	version = 4
)
@TypeConverters(UserPriorityConverter::class, SensitivityConverter::class)
internal abstract class FallDetectorDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    abstract fun serviceStateDao(): ServiceStateDao
}