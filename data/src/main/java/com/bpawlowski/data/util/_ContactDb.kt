package com.bpawlowski.data.util

import com.bpawlowski.core.model.Contact
import com.bpawlowski.database.entity.ContactDb

internal fun ContactDb.toDomain() = Contact(
	id = id,
	name = name,
	mobile = mobile,
	email = email,
	priority = priority,
	photoPath = photoPath
)

internal fun Contact.toEntity() = ContactDb(
	id = id,
	name = name,
	mobile = mobile,
	email = email,
	priority = priority,
	photoPath = photoPath
)