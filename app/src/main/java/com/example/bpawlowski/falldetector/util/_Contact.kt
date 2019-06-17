package com.example.bpawlowski.falldetector.util

import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.domain.ContactForm

fun Contact.copyToForm(form: ContactForm) =
    form.apply {
        name = this@copyToForm.name
        mobile = this@copyToForm.mobile.toString()
        email = this@copyToForm.email ?: String.empty
        priority = this@copyToForm.priority == UserPriority.PRIORITY_ICE
    }


fun ContactForm.mapToContact(): Contact =
    Contact(
        name = name,
        mobile = mobile.toInt(),
        email = email,
        priority = if (priority) UserPriority.PRIORITY_ICE else UserPriority.PRIORITY_NORMAL
    )