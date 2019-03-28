package com.example.bpawlowski.falldetector.service.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(
    indices = [Index("mobile", unique = true)],
    tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id", typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    var id: Long? = null,

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    @NonNull
    var name: String,

    @ColumnInfo(name = "mobile", typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    var mobile: Int,

    @ColumnInfo(name = "email", typeAffinity = ColumnInfo.TEXT)
    var email: String?,

    @ColumnInfo(name = "user_priority")
    var priority: UserPriority
){
    @Ignore
    constructor() : this(null,"",0,null, UserPriority.PRIORITY_NORMAL)

}