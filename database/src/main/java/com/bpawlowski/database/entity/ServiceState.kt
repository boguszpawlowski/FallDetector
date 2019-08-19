package com.bpawlowski.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "service_state")
data class ServiceState(
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
) {

	@Ignore
	constructor() : this(null, false, Sensitivity.HIGH, true, true)
}