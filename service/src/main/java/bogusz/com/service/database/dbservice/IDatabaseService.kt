package bogusz.com.service.database.dbservice

import bogusz.com.service.database.FallDetectorDatabase
import bogusz.com.service.database.dao.ContactDao

interface IDatabaseService{

    fun getDatabaseInstance(): FallDetectorDatabase

    fun getContactDao(): ContactDao
}