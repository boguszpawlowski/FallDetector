package com.example.bpawlowski.falldetector.screens.main.contacts

import com.example.bpawlowski.falldetector.domain.ContactFormModel
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Uninitialized

data class AddContactViewState(
    val saveContactRequest: StateValue<Unit> = Uninitialized,
    val contactForm: ContactFormModel = ContactFormModel()
) : MviState
