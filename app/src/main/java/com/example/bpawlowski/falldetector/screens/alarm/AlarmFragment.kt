package com.example.bpawlowski.falldetector.screens.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentAlarmBinding
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.screens.common.compose.AlarmScreen
import com.example.bpawlowski.falldetector.util.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmFragment : BaseFragment<AlarmViewState>() {

    override val viewModel: AlarmViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlarmBinding.inflate(inflater, container, false)

        binding.composeView.setContent {
            AlarmScreen(onClick = viewModel::raiseAlarm)
        }

        return binding.root
    }
    
    override fun invalidate(state: AlarmViewState) {
        state.alarmRequest.onFailure {
            snackbar(message = it.rationale)
        }
    }
}
