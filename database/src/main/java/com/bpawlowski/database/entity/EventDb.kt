package com.bpawlowski.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EventDb(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "Id", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val id: Long?,

	@ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
	@NonNull
	val title: String,

	@ColumnInfo(name = "creator_id", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val creatorId: Long,

	@ColumnInfo(name = "date", typeAffinity = ColumnInfo.TEXT)
	@NonNull
	val date: String,

	@ColumnInfo(name = "remote_id", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val remoteId: Long?,

	@ColumnInfo(name = "latitude", typeAffinity = ColumnInfo.TEXT)
	@NonNull
	val latitude: String,

	@ColumnInfo(name = "longitude", typeAffinity = ColumnInfo.TEXT)
	@NonNull
	val longitude: String,

	@ColumnInfo(name = "attending_users", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val attending: Int,

	@ColumnInfo(name = "is_attending", typeAffinity = ColumnInfo.INTEGER)
	@NonNull
	val isAttending: Boolean,

	@ColumnInfo(name = "creator_mobile", typeAffinity = ColumnInfo.TEXT)
	@NonNull
	val creatorName: String
)