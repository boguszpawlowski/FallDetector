package com.bpawlowski.service.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
	indices = [Index("mobile", unique = true)],
	tableName = "contact"
)
data class Contact(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "Id", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val id: Long? = null,

	@ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
	@NonNull
	val name: String,

	@ColumnInfo(name = "mobile", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val mobile: Int,

	@ColumnInfo(name = "email", typeAffinity = ColumnInfo.TEXT)
	@Nullable
	val email: String?,

	@ColumnInfo(name = "user_priority")
	@NonNull
	val priority: UserPriority,

	@ColumnInfo(name = "photo_path", typeAffinity = ColumnInfo.TEXT)
	@Nullable
	val photoPath: String? = null
)