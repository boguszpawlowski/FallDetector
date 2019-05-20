package com.example.bpawlowski.falldetector.ui.main

import androidx.databinding.ObservableBoolean
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    val stateSubject = BehaviorSubject.create<MainScreenState>()

    var isLoading: ObservableBoolean = ObservableBoolean(false)

    fun changeState(state: MainScreenState) {
        isLoading.set(state is MainScreenState.LoadingState)
        stateSubject.onNext(state)
    }
}
