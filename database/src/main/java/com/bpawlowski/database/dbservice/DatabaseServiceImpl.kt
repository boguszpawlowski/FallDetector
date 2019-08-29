package com.bpawlowski.database.dbservice

import android.content.Context
import androidx.room.Room
import com.bpawlowski.database.FallDetectorDatabase
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dao.EventDao
import com.bpawlowski.database.dao.ServiceStateDao

private const val name = "FDDatabase"

internal class DatabaseServiceImpl(
	context: Context
) : DatabaseService {

	private var dbInstance: FallDetectorDatabase

	init {
		dbInstance = Room.databaseBuilder(
			context,
			FallDetectorDatabase::class.java,
			name
		).fallbackToDestructiveMigration()
			.build()
	}

	override fun getContactDao(): ContactDao = dbInstance.contactDao()

	override fun getServiceStateDao(): ServiceStateDao = dbInstance.serviceStateDao()

	override fun getEventDao(): EventDao = dbInstance.eventDao()
}