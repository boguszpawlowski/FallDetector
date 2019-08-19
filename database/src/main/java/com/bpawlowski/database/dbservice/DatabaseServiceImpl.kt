package com.bpawlowski.database.dbservice

import android.content.Context
import androidx.room.Room
import com.bpawlowski.database.FallDetectorDatabase
import com.bpawlowski.database.dao.ContactDao
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

	override fun getDatabaseInstance(): FallDetectorDatabase = dbInstance

	override fun getContactDao(): ContactDao = getDatabaseInstance().contactDao()

	override fun getServiceStateDao(): ServiceStateDao = getDatabaseInstance().serviceStateDao()
}