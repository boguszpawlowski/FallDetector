package com.bpawlowski.database.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bpawlowski.domain.model.Contact

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
    val priority: Boolean,

    @ColumnInfo(name = "photo_path", typeAffinity = ColumnInfo.TEXT)
    @Nullable
    val photoPath: String? = null
)

fun ContactDb.toDomain() = Contact(
    id = id,
    name = name,
    mobile = mobile,
    email = email,
    isIce = priority,
    photoPath = photoPath
)

fun Contact.toEntity() = ContactDb(
    id = id,
    name = name,
    mobile = mobile,
    email = email,
    priority = isIce,
    photoPath = photoPath
)
