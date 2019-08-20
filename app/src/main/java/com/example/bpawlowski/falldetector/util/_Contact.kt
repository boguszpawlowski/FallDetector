package com.example.bpawlowski.falldetector.util

import com.bpawlowski.core.model.Contact
import com.example.bpawlowski.falldetector.domain.ContactFormModel

fun ContactFormModel.mapToContact() = Contact(
	name = name,
	mobile = mobile.toInt(),
	email = email,
	priority = if (priority) com.bpawlowski.core.model.ContactPriority.PRIORITY_ICE else com.bpawlowski.core.model.ContactPriority.PRIORITY_NORMAL,
	photoPath = filePath
)