package com.bpawlowski.database.dbservice

import com.bpawlowski.database.FallDetectorDatabase
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dao.EventDao
import com.bpawlowski.database.dao.ServiceStateDao

interface DatabaseService {

    fun getContactDao(): ContactDao

    fun getServiceStateDao(): ServiceStateDao

    fun getEventDao(): EventDao
}