package com.bpawlowski.database.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.bpawlowski.core.model.ContactPriority

@Entity(
	indices = [Index("mobile", unique = true)],
	tableName = "contact"
)
data class ContactDb(
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
	val priority: ContactPriority,

	@ColumnInfo(name = "photo_path", typeAffinity = ColumnInfo.TEXT)
	@Nullable
	val photoPath: String? = null
)

internal object UserPriorityConverter {
	@TypeConverter
	@JvmStatic
	fun toPriority(priority: Int): ContactPriority {
		return when (priority) {
			0 -> ContactPriority.PRIORITY_NORMAL
			1 -> ContactPriority.PRIORITY_ICE
			else -> ContactPriority.PRIORITY_NORMAL
		}
	}

	@TypeConverter
	@JvmStatic
	fun toInt(priority: ContactPriority): Int = priority.ordinal
}