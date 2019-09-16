package com.bpawlowski.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dao.EventDao
import com.bpawlowski.database.dao.ServiceStateDao
import com.bpawlowski.database.entity.*

@Database(
	entities = [ContactDb::class, ServiceStateDb::class, EventDb::class],
	version = 9
)
@TypeConverters(UserPriorityConverter::class, SensitivityConverter::class)
internal abstract class FallDetectorDatabase : RoomDatabase() {

	abstract fun contactDao(): ContactDao

	abstract fun serviceStateDao(): ServiceStateDao

	abstract fun eventDao(): EventDao
}