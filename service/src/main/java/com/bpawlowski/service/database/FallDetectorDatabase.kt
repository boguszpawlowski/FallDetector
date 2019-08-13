package com.bpawlowski.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bpawlowski.service.database.dao.ContactDao
import com.bpawlowski.service.database.dao.ServiceStateDao
import com.bpawlowski.service.model.Contact
import com.bpawlowski.service.model.SensitivityConverter
import com.bpawlowski.service.model.ServiceState
import com.bpawlowski.service.model.UserPriorityConverter

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