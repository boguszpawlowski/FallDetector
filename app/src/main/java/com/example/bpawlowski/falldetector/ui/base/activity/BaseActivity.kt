package com.example.bpawlowski.falldetector.ui.base.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.bpawlowski.falldetector.BR
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).v("ON_CREATE")
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        val viewBinding = DataBindingUtil.setContentView<B>(this, getLayoutID())
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

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    fun navigateToActivity(activity: Activity) {
        Intent(this, activity::class.java).run {
            startActivity(intent)
        }

        if (keepInBackStack.not()) {
            finish()
        }
    }

    open val keepInBackStack: Boolean = false

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getLayoutID(): Int
}