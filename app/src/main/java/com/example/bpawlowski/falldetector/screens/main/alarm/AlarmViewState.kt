package com.example.bpawlowski.falldetector.screens.main.alarm

import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Uninitialized

data class AlarmViewState(
    val alarmRequest: StateValue<Unit> = Uninitialized
) : MviState
