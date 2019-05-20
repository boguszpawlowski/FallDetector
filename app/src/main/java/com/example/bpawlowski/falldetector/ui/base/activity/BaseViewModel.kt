package com.example.bpawlowski.falldetector.ui.base.activity

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposable = CompositeDisposable()

    open fun onResume(){}

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}