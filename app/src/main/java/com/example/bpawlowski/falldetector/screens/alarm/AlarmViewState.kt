package com.example.bpawlowski.falldetector.screens.alarm

import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.domain.StateValue
import com.example.bpawlowski.falldetector.domain.StateValue.Idle

data class AlarmViewState(
    val alarmRequest: StateValue<Unit> = Idle
) : MviState
