/**
 *Based on: https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/AutoClearedValue.kt
 *With some changes to clear value in onDestroyView callback, rather then onDestroy
 */

package com.example.bpawlowski.falldetector.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClearedValue<T : Any>(val fragment: Fragment) : ReadWriteProperty<Fragment, T> {
	private var _value: T? = null

	private val lifecycleCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
		override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
			if (f == fragment) {
				_value = null
			}
		}
	}

	init {
		fragment.lifecycle.addObserver(object : LifecycleObserver {
			@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
			fun onCreate() {
				fragment.requireFragmentManager().registerFragmentLifecycleCallbacks(
					lifecycleCallback, false
				)
			}

			@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
			fun onDestroy() {
				fragment.requireFragmentManager().unregisterFragmentLifecycleCallbacks(lifecycleCallback)
			}
		})
	}

	override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
		return _value ?: throw IllegalStateException(
			"should never call auto-cleared-value get when it might not be available"
		)
	}

	override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
		_value = value
	}
}

fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)