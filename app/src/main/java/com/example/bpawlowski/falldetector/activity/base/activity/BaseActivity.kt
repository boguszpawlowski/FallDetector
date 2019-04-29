package com.example.bpawlowski.falldetector.activity.base.activity

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bpawlowski.falldetector.BR
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var binding: B

    internal val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("ON_CREATE")
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        binding = DataBindingUtil.setContentView(this, getLayoutID())
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun onStart() {
        Timber.d("ON_START")
        super.onStart()
    }

    override fun onResume() {
        Timber.d("ON_RESUME")
        super.onResume()
    }

    override fun onPause() {
        Timber.d("ON_PAUSE")
        super.onPause()
    }

    override fun onStop() {
        Timber.d("ON_STOP")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.d("ON_DESTROY")
        super.onDestroy()

        disposable.dispose()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    fun navigateToActivity(activity: Activity){
        val intent = Intent(this, activity::class.java)

        startActivity(intent)

        if(!keepInBackStack()){
            finish()
        }
    }

    open fun keepInBackStack(): Boolean = false

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getLayoutID(): Int
}