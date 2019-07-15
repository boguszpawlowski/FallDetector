package com.example.bpawlowski.falldetector.ui.main.alarm

import android.os.Bundle
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentAlarmBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmFragment : BaseFragment<AlarmViewModel, MainViewModel, FragmentAlarmBinding>() {

	override val viewModel: AlarmViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAlarm.setOnClickListener {
            viewModel.raiseAlarm()
        }
    }


    override fun getLayoutID() = R.layout.fragment_alarm
}