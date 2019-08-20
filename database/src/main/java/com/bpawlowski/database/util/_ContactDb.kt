package com.bpawlowski.database.util

import com.bpawlowski.core.model.Contact
import com.bpawlowski.database.entity.ContactDb

internal fun ContactDb.toContact() = Contact(
	id = id,
	name = name,
	mobile = mobile,
	email = email,
	priority = priority,
	photoPath = photoPath
)

internal fun Contact.toContactDb() = ContactDb(
	id = id,
	name = name,
	mobile = mobile,
	email = email,
	priority = priority,
	photoPath = photoPath
)