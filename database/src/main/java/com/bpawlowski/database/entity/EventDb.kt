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

    @ColumnInfo(name = "location", typeAffinity = ColumnInfo.TEXT)
    @NonNull
    val location: String,

    @ColumnInfo(name = "date", typeAffinity = ColumnInfo.TEXT)
    @NonNull
    val date: String,

    @ColumnInfo(name = "remote_id", typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    val remoteId: Long?
)