package com.example.bpawlowski.falldetector.service.database.dbservice

import com.example.bpawlowski.falldetector.service.database.FallDetectorDatabase
import com.example.bpawlowski.falldetector.service.database.dao.ContactDao

interface IDatabaseService{

    fun getDatabaseInstance(): FallDetectorDatabase

    fun getContactDao(): ContactDao
}