package bogusz.com.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bogusz.com.service.database.dao.ContactDao
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriorityConverter

@Database(
    entities = arrayOf(
        Contact::class
    ),
    version = 1
)
@TypeConverters(UserPriorityConverter::class)
internal abstract class FallDetectorDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}