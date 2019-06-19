package com.example.bpawlowski.falldetector.ui.main.home

import android.os.Bundle
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentHomeBinding
import com.example.bpawlowski.falldetector.monitoring.BackgroundService
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel

class HomeFragment : BaseFragment<HomeViewModel, MainViewModel, FragmentHomeBinding>() {
//TODO add database table for Service Flags

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMonitor.setOnClickListener {
            if (BackgroundService.isRunning) {
                BackgroundService.stopService(requireActivity())
            } else {
                BackgroundService.startService(requireActivity())
            }
        }
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutID(): Int = R.layout.fragment_home

    override fun getSharedViewModeClass(): Class<MainViewModel> = MainViewModel::class.java
}
