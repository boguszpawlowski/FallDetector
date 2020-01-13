package com.example.bpawlowski.falldetector.screens.main.contacts

import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue

data class ContactsViewState(
    val removeContactRequest: StateValue<Contact> = StateValue.Uninitialized,
    val contacts: StateValue<List<Contact>> = StateValue.Loading
) : MviState
