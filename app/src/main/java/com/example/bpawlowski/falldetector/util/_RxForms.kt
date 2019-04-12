package com.example.bpawlowski.falldetector.util

import androidx.databinding.ObservableField
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

inline fun <T> Observable<T>.validate(
    errorObservable: ObservableField<String>? = null,
    errorMessage: String? = null,
    debounceMilliseconds: Long = 250,
    crossinline predicate: (T) -> Boolean
): Observable<Boolean> {
    return this.debounce(debounceMilliseconds, TimeUnit.MILLISECONDS)
        .map { predicate.invoke(it) }
        .doAfterFirst { if (!it) errorObservable?.set(errorMessage) else errorObservable?.set(null) }
        .distinctUntilChanged()
}

inline fun <T> Observable<T>.doAfterFirst(crossinline block: (T) -> Unit) =
    scan { _, t2 ->
        block(t2)
        t2
    }