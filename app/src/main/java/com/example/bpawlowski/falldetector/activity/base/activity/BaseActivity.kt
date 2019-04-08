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
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        binding = DataBindingUtil.setContentView(this, getLayoutID())
        bindViewModel()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    fun navigateToActivity(activity: Activity){

        val intent = Intent(this, activity::class.java)

        startActivity(intent)

        if(!keepInBackStack()){
            finish()
        }
    }
    //TODO navigate to new view - fragment manager

    open fun keepInBackStack(): Boolean = false

    abstract fun getViewModelClass(): Class<VM>

    abstract fun bindViewModel()

    abstract fun getLayoutID(): Int

}