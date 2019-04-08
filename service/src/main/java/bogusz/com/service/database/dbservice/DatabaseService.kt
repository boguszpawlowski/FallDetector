package bogusz.com.service.database.dbservice

import android.content.Context
import androidx.room.Room
import bogusz.com.service.database.FallDetectorDatabase
import bogusz.com.service.database.dao.ContactDao
import javax.inject.Inject

class DatabaseService @Inject constructor(
     context: Context
): IDatabaseService{

    private var dbInstance: FallDetectorDatabase

    init {
        dbInstance = Room.databaseBuilder(
            context,
            FallDetectorDatabase::class.java,
            name
        ).build()
    }

    override fun getDatabaseInstance(): FallDetectorDatabase = dbInstance

    override fun getContactDao(): ContactDao = getDatabaseInstance().contactDao()

    companion object {
        const val name = "FDDatabase"
    }
}