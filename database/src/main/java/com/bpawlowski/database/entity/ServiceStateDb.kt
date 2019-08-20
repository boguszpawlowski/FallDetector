package com.bpawlowski.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.bpawlowski.core.model.Sensitivity

@Entity(tableName = "service_state")
data class ServiceStateDb(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val id: Long? = null,

	@ColumnInfo(name = "is_running", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val isRunning: Boolean,

	@ColumnInfo(name = "sensitivity")
	@NonNull
	val sensitivity: Sensitivity,

	@ColumnInfo(name = "sending_sms")
	@NonNull
	val sendingSms: Boolean,

	@ColumnInfo(name = "sending_location")
	@NonNull
	val sendingLocation: Boolean
){
	@Ignore
	constructor() : this(null, false, Sensitivity.HIGH, true, true)
}

internal object SensitivityConverter {
	@TypeConverter
	@JvmStatic
	fun toSensitivity(sensitivity: Int): Sensitivity {
		return when (sensitivity) {
			0 -> Sensitivity.LOW
			1 -> Sensitivity.MEDIUM
			2 -> Sensitivity.HIGH
			else -> Sensitivity.HIGH
		}
	}

	@TypeConverter
	@JvmStatic
	fun toInt(sensitivity: Sensitivity): Int = sensitivity.ordinal
}