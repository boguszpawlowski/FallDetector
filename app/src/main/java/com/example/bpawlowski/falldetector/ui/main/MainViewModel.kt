package com.example.bpawlowski.falldetector.ui.main

import android.content.SharedPreferences
import androidx.databinding.ObservableBoolean
import bogusz.com.service.preferences.AppSettingsPreferencesData
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    val appSettingsPreferencesData: AppSettingsPreferencesData
        get() = AppSettingsPreferencesData(sharedPreferences)

    val stateSubject = BehaviorSubject.create<MainScreenState>()

    var isLoading: ObservableBoolean = ObservableBoolean(false)

    fun changeState(state: MainScreenState) {
        isLoading.set(state is MainScreenState.LoadingState)
        stateSubject.onNext(state)
    }
}
