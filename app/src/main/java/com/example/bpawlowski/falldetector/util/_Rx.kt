package com.example.bpawlowski.falldetector.util

import androidx.databinding.ObservableField
import io.reactivex.Observable

fun <T> ObservableField<T>.toObservable(): Observable<T> =
    Observable.create { emitter ->

        val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: androidx.databinding.Observable?, propertyId: Int) {
                if (sender == this) {
                    get()?.let {
                        emitter.onNext(it)
                    }
                }
            }
        }
        this.addOnPropertyChangedCallback(callback)
        emitter.setCancellable { this.removeOnPropertyChangedCallback(callback) }
    }