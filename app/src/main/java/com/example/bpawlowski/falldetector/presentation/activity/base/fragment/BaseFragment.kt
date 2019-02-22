package com.example.bpawlowski.falldetector.presentation.activity.base.fragment

import android.app.Activity
import android.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment<VM: ViewModel, B: ViewDataBinding>: Fragment(){
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        //binding = DataBindingUtil.setContentView(this, getLayoutID())
    }

    open fun keepInBackStack(): Boolean = false

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getLayoutID(): Int

    abstract fun getParentActivity(): Activity
}