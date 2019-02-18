package com.example.bpawlowski.falldetector.presentation.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val TAG = MainActivity::class.java.simpleName


    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        subscribeToSubject()
    }

    @SuppressLint("CheckResult")
    private fun subscribeToSubject() {

    }
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutID(): Int = R.layout.activity_main


}
