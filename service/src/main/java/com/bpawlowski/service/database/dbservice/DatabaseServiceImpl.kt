package com.bpawlowski.service.database.dbservice

import android.content.Context
import androidx.room.Room
import com.bpawlowski.service.database.FallDetectorDatabase
import com.bpawlowski.service.database.dao.ContactDao
import com.bpawlowski.service.database.dao.ServiceStateDao

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

	override fun getDatabaseInstance(): FallDetectorDatabase = dbInstance

	override fun getContactDao(): ContactDao = getDatabaseInstance().contactDao()

	override fun getServiceStateDao(): ServiceStateDao = getDatabaseInstance().serviceStateDao()
}