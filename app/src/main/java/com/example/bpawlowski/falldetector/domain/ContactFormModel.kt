package com.example.bpawlowski.falldetector.domain

import android.util.Patterns
import com.bpawlowski.domain.model.Contact
import com.bpawlowski.domain.model.ContactPriority.PRIORITY_ICE
import com.bpawlowski.domain.model.ContactPriority.PRIORITY_NORMAL
import com.example.bpawlowski.falldetector.util.empty

data class ContactFormModel(
    val name: String = String.empty,
    val mobile: String = String.empty,
    val email: String = String.empty,
    val priority: Boolean = false,
    val filePath: String? = null
) {

    val nameError: String?
        get() = if (nameIsValid.not()) "Name can't be empty" else null

    val mobileError: String?
        get() = if (mobileIsValid.not()) "Mobile must contain 9 digits" else null

    val emailError: String?
        get() = if (emailIsValid.not()) "Email is not valid" else null

    val isValid: Boolean
        get() = nameIsValid && mobileIsValid && emailIsValid

    private val nameIsValid
        get() = name.isNotBlank()
    private val mobileIsValid
        get() = mobile.matches(Regex("\\d{9}"))
    private val emailIsValid
        get() = email.matches(Patterns.EMAIL_ADDRESS.toRegex()) or email.isEmpty()

    fun hasChanged(contact: Contact?): Boolean {
        return name != contact?.name ||
                mobile != contact.mobile.toString() ||
                email != contact.email ||
                priority != (contact.priority == PRIORITY_ICE) ||
                filePath != contact.photoPath
    }
}

fun Contact.toForm() = ContactFormModel(
    name = name,
    email = email.toString(),
    mobile = mobile.toString(),
    priority = priority == PRIORITY_ICE,
    filePath = photoPath
)

fun ContactFormModel.toContact() = Contact(
    name = name,
    mobile = mobile.toInt(),
    email = email,
    priority = if (priority) PRIORITY_ICE else PRIORITY_NORMAL,
    photoPath = filePath
)
