package com.example.bpawlowski.falldetector.screens.main.details

import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Uninitialized

data class ContactDetailsViewState(
    val saveContactRequest: StateValue<Unit> = Uninitialized,
    val contactForm: ContactFormModel = ContactFormModel(),
    val contact: StateValue<Contact> = Uninitialized
) : MviState
