package com.example.bpawlowski.falldetector.screens.contacts.add

import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Idle

data class AddContactViewState(
    val saveContactRequest: StateValue<Unit> = Idle
) : MviState
