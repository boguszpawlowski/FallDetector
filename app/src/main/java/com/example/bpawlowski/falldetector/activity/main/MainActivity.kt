package com.example.bpawlowski.falldetector.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.activity.main.recycler.PostContainersViewAdapter
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val TAG = MainActivity::class.java.simpleName


    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initTicketsRecycler()

        subscribeToSubject()
    }

    @SuppressLint("CheckResult")
    private fun subscribeToSubject() {
        viewModel.postsSubject.subscribe(
            {
                it?.let {
                    Log.e(TAG, "get info from subject")
                    (binding.postList.adapter as? PostContainersViewAdapter)?.data = it
                }
            },
            {
                it.message?.let { showError(it) }
            })
    }

    private fun initTicketsRecycler() {
        binding.postList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostContainersViewAdapter()
        }
    }


    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutID(): Int = R.layout.activity_main

    private fun showError(message: String) {
        errorSnackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

}
