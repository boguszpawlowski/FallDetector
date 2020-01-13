/**
 *Based on: https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/AutoClearedLazyValue.kt
 *With some changes to clear value in onDestroyView callback and for value to be computed lazily
 */

package com.example.bpawlowski.falldetector.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class AutoClearedLazyValue<T : Any>(val initializer: () -> T) : ReadOnlyProperty<Fragment, T>,
    LifecycleObserver {

    private var _value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val viewLifecycle = thisRef.viewLifecycleOwner.lifecycle

        check(viewLifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            "You should never call auto-cleared-value get when it might not be available"
        }

        viewLifecycle.addObserver(this)
        if (_value == null) {
            _value = initializer()
        }
        viewLifecycle.removeObserver(this)

        return checkNotNull(_value) { "This can't be null" }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        _value = null
    }
}

fun <T : Any> autoClearedLazy(initializer: () -> T) = AutoClearedLazyValue(initializer)
