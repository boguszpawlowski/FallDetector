package com.example.bpawlowski.falldetector.screens.contacts.list

import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue

data class ContactsViewState(
    val removeContactRequest: StateValue<Contact> = StateValue.Idle,
    val contacts: List<Contact> = emptyList()
) : MviState
