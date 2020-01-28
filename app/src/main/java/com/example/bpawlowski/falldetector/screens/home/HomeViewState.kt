package com.example.bpawlowski.falldetector.screens.home

import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Idle

data class HomeViewState(
    val toggleServiceRequest: StateValue<Unit> = Idle
) : MviState
