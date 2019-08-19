package com.example.bpawlowski.falldetector.util

import com.bpawlowski.database.entity.Contact
import com.bpawlowski.database.entity.UserPriority
import com.example.bpawlowski.falldetector.domain.ContactFormModel

fun ContactFormModel.mapToContact(): Contact =
    Contact(
        name = name,
        mobile = mobile.toInt(),
        email = email,
		priority = if (priority) UserPriority.PRIORITY_ICE else UserPriority.PRIORITY_NORMAL,
		photoPath = filePath
    )