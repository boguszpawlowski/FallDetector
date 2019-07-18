package com.example.bpawlowski.falldetector.ui.base.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

	protected val backgroundScope = viewModelScope + Dispatchers.Default

    open fun onResume(){}

    override fun onCleared() {
        Timber.tag(javaClass.simpleName).v("ON_CLEARED")
        super.onCleared()
    }
}