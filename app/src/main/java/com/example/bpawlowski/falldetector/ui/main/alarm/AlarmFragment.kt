package com.example.bpawlowski.falldetector.ui.main.alarm

import android.os.Bundle
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentAlarmBinding
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmFragment : BaseFragment<FragmentAlarmBinding>() {

	override val layoutResID = R.layout.fragment_alarm

	override val viewModel: AlarmViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAlarm.setOnClickListener {
            viewModel.raiseAlarm()
        }
    }
}