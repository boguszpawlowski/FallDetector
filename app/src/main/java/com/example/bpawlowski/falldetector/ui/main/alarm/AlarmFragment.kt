package com.example.bpawlowski.falldetector.ui.main.alarm

import android.os.Bundle
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentAlarmBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel

class AlarmFragment : BaseFragment<AlarmViewModel, MainViewModel, FragmentAlarmBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnAlarm.setOnClickListener {
            viewModel.raiseAlarm()
        }
    }

    override fun getViewModelClass() = AlarmViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_alarm

    override fun getParentViewModeClass() = MainViewModel::class.java
}