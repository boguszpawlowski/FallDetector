package com.example.bpawlowski.falldetector.base.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.bpawlowski.falldetector.BR
import timber.log.Timber

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

	abstract val viewModel: BaseViewModel

	lateinit var binding: B

	override fun onCreate(savedInstanceState: Bundle?) {
		Timber.tag(javaClass.simpleName).v("ON_CREATE")
		super.onCreate(savedInstanceState)

		val viewBinding = DataBindingUtil.setContentView<B>(this, layoutId)
		binding = viewBinding
		binding.setVariable(BR.viewModel, viewModel)
	}

	override fun onStart() {
		Timber.tag(javaClass.simpleName).v("ON_START")
		super.onStart()
	}

	override fun onResume() {
		Timber.tag(javaClass.simpleName).v("ON_RESUME")
		super.onResume()
	}

	override fun onPause() {
		Timber.tag(javaClass.simpleName).v("ON_PAUSE")
		super.onPause()
	}

	override fun onStop() {
		Timber.tag(javaClass.simpleName).v("ON_STOP")
		super.onStop()
	}

	override fun onDestroy() {
		Timber.tag(javaClass.simpleName).v("ON_DESTROY")
		super.onDestroy()
	}

	fun navigateToActivity(activity: Activity) {
		Intent(this, activity::class.java).run {
			startActivity(intent)
		}
	}

	abstract val layoutId: Int
}