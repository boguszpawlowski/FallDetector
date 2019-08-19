package com.bpawlowski.database.dbservice

import com.bpawlowski.database.FallDetectorDatabase
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dao.ServiceStateDao

internal interface DatabaseService {

    fun getDatabaseInstance(): FallDetectorDatabase

    fun getContactDao(): ContactDao

    fun getServiceStateDao(): ServiceStateDao
}