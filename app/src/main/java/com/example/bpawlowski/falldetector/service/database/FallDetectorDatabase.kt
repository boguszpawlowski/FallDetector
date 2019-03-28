package com.example.bpawlowski.falldetector.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bpawlowski.falldetector.service.database.dao.ContactDao
import com.example.bpawlowski.falldetector.service.model.Contact
import com.example.bpawlowski.falldetector.service.model.UserPriorityConverter

@Database(
    entities = arrayOf(
        Contact::class
    ),
    version = 1
)
@TypeConverters(UserPriorityConverter::class)
abstract class FallDetectorDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}