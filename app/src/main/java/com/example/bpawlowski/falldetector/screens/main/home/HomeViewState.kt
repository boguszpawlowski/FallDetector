package com.example.bpawlowski.falldetector.screens.main.home

import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Uninitialized

data class HomeViewState(
    val toggleServiceRequest: StateValue<Unit> = Uninitialized
) : MviState
