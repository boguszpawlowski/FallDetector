package com.example.bpawlowski.falldetector.screens.camera

import com.example.bpawlowski.falldetector.domain.MviState

data class CameraViewState(
    val photoPath: String? = null
) : MviState
