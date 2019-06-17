package com.example.bpawlowski.falldetector.ui.base.activity

import androidx.lifecycle.ViewModel
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    open fun onResume(){}

    override fun onCleared() {
        Timber.tag(javaClass.simpleName).v("ON_CLEARED")
        super.onCleared()
    }
}