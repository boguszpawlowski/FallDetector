package bogusz.com.service.database.dbservice

import bogusz.com.service.database.FallDetectorDatabase
import bogusz.com.service.database.dao.ContactDao

internal interface DatabaseService{

    fun getDatabaseInstance(): FallDetectorDatabase

    fun getContactDao(): ContactDao
}