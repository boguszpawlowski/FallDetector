package com.example.bpawlowski.falldetector.screens.main.events

import com.bpawlowski.domain.model.Event
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Uninitialized

data class EventDetailsViewState(
    val eventData: StateValue<Event> = Uninitialized,
    val updateAttendingRequest: StateValue<Unit> = Uninitialized
) : MviState
