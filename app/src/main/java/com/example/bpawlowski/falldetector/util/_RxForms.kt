package com.example.bpawlowski.falldetector.util

import androidx.databinding.ObservableField
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.validate(
    errorObservable: ObservableField<String>? = null,
    errorMessage: String? = null,
    debounceMilliseconds: Long = 250,
    predicate: (T) -> Boolean
): Observable<Boolean> {
    return this.debounce(debounceMilliseconds, TimeUnit.MILLISECONDS)
        .map { predicate.invoke(it) }
        .doAfterFirst { if (!it) errorObservable?.set(errorMessage) else errorObservable?.set(null) }
        .distinctUntilChanged()
}

fun <T> Observable<T>.doAfterFirst(block: (T) -> Unit) =
    scan { _, t2 ->
        block(t2)
        t2
    }