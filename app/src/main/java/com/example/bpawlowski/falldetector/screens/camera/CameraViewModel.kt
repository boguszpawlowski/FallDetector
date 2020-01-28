package com.example.bpawlowski.falldetector.screens.camera

import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import java.io.File

class CameraViewModel(
    initialState: CameraViewState = CameraViewState()
) : BaseViewModel<CameraViewState>(initialState) {

    fun onPhotoCaptured(file: File) = setState {
        copy(photoPath = file.toURI().toString())
    }

    /**
     * since CameraViewModel is bound to activity lifecycle we have to reset state after receiving photo path
     */
    fun resetPhotoPath() = setState {
        copy(photoPath = null)
    }
}
