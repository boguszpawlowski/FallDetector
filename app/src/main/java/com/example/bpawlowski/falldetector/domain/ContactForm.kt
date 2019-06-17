package com.example.bpawlowski.falldetector.domain

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.util.empty

data class ContactForm( //TODO this class should extend observable
    val name: ObservableField<String>,
    val mobile: ObservableField<String>,
    val email: ObservableField<String>,
    val priority: ObservableBoolean
) {
    constructor() : this(
        ObservableField(""),
        ObservableField(""),
        ObservableField(""),
        ObservableBoolean(false)
    )
}

fun Contact.copyToForm(form: ContactForm) =
    form.apply {
        name.set(this@copyToForm.name)
        mobile.set(this@copyToForm.mobile.toString())
        email.set(this@copyToForm.email)
        priority.set(this@copyToForm.priority == UserPriority.PRIORITY_ICE)
    }


fun ContactForm.mapToContact(): Contact =
    Contact(
        name = name.get() ?: String.empty,
        mobile = mobile.get()?.toInt() ?: 0,
        email = email.get(),
        priority = if (priority.get()) UserPriority.PRIORITY_ICE else UserPriority.PRIORITY_NORMAL
    )