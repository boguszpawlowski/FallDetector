package com.example.bpawlowski.falldetector.screens.contacts.details

import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Idle

data class ContactDetailsViewState(
    val saveContactRequest: StateValue<Unit> = Idle,
    val contact: StateValue<Contact> = Idle
) : MviState
