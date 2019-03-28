package com.example.bpawlowski.falldetector.presentation.activity.main

sealed class MainScreenState{
    data class ErrorState(val error: Throwable): MainScreenState()
    data class SuccessState(val data: Any?): MainScreenState()
    object LoadingState: MainScreenState()
    object CompletedState: MainScreenState()
}