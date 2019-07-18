package bogusz.com.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bogusz.com.service.database.dao.ContactDao
import bogusz.com.service.database.dao.ServiceStateDao
import bogusz.com.service.model.Contact
import bogusz.com.service.model.SensitivityConverter
import bogusz.com.service.model.ServiceState
import bogusz.com.service.model.UserPriorityConverter

@Database(
    entities = arrayOf(
        Contact::class,
        ServiceState::class
    ),
	version = 3
)
@TypeConverters(UserPriorityConverter::class, SensitivityConverter::class)
internal abstract class FallDetectorDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    abstract fun serviceStateDao(): ServiceStateDao
}