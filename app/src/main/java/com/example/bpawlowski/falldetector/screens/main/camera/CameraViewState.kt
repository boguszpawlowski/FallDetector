package com.example.bpawlowski.falldetector.screens.main.camera

import com.example.bpawlowski.falldetector.domain.MviState

data class CameraViewState(
    val photoPath: String? = null
) : MviState
