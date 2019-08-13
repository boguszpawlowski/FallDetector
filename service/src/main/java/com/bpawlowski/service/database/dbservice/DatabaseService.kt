package com.bpawlowski.service.database.dbservice

import com.bpawlowski.service.database.FallDetectorDatabase
import com.bpawlowski.service.database.dao.ContactDao
import com.bpawlowski.service.database.dao.ServiceStateDao

internal interface DatabaseService {

    fun getDatabaseInstance(): FallDetectorDatabase

    fun getContactDao(): ContactDao

    fun getServiceStateDao(): ServiceStateDao
}