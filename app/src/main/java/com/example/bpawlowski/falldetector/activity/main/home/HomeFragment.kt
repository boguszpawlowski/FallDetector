package com.example.bpawlowski.falldetector.activity.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.ObservableBoolean
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.activity.main.MainViewModel
import com.example.bpawlowski.falldetector.databinding.FragmentHomeBinding
import com.example.bpawlowski.falldetector.monitoring.BackgroundService
import io.reactivex.android.schedulers.AndroidSchedulers

class HomeFragment : BaseFragment<HomeViewModel, MainViewModel, FragmentHomeBinding>() {

    private val isServiceRunning = ObservableBoolean(false) //TODO add database table for Service Flags

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        BackgroundService.isServiceRunningSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isServiceRunning.set(it) }

        binding.btnMonitor.setOnClickListener {
            if (isServiceRunning.get().not()) {
                BackgroundService.startService(requireActivity())
            } else {
                BackgroundService.stopService(requireActivity())
            }
        }
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutID(): Int = R.layout.fragment_home

    override fun getParentViewModeClass(): Class<MainViewModel> = MainViewModel::class.java
}
