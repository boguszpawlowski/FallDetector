package bogusz.com.service.util

import androidx.lifecycle.*

fun <T, R> LiveData<T>.map(transform: (T) -> R): LiveData<R> =
    Transformations.map(this) { transform.invoke(it) }

fun <T, R> LiveData<T>.switchMap(transform: (T) -> LiveData<R>): LiveData<R> =
    Transformations.switchMap(this) { transform.invoke(it) }

inline fun <T, R : Comparable<R>> LiveData<List<T>>.sortedBy(crossinline selector: (T) -> R?): LiveData<List<T>> =
    map { it.sortedWith(compareBy(selector)) }

fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}

fun <T1, T2, T3, R> zip(
    src1: LiveData<T1>,
    src2: LiveData<T2>,
    src3: LiveData<T3>,
    zipper: (T1, T2, T3) -> R
): LiveData<R> {

    return MediatorLiveData<R>().apply {
        var src1Version = 0
        var src2Version = 0
        var src3Version = 0

        var lastSrc1: T1? = null
        var lastSrc2: T2? = null
        var lastSrc3: T3? = null

        fun updateValueIfNeeded() {
            if (src1Version > 0 && src2Version > 0 && src3Version > 0 &&
                lastSrc1 != null && lastSrc2 != null && lastSrc3 != null
            ) {
                value = zipper(lastSrc1!!, lastSrc2!!, lastSrc3!!)
                src1Version = 0
                src2Version = 0
            }
        }

        addSource(src1) {
            lastSrc1 = it
            src1Version++
            updateValueIfNeeded()
        }

        addSource(src2) {
            lastSrc2 = it
            src2Version++
            updateValueIfNeeded()
        }

        addSource(src3) {
            lastSrc3 = it
            src3Version++
            updateValueIfNeeded()
        }
    }
}