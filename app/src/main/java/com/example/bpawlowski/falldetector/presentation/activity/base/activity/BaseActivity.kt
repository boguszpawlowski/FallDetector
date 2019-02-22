package com.example.bpawlowski.falldetector.presentation.activity.base.activity

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        binding = DataBindingUtil.setContentView(this, getLayoutID())

    }

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