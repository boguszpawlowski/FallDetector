package com.example.bpawlowski.falldetector.screens.main.alarm

import android.os.Bundle
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.util.snackbar
import kotlinx.android.synthetic.main.fragment_alarm.buttonAlarm
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmFragment : BaseFragment<AlarmViewState>() {

    override val layoutResID = R.layout.fragment_alarm

    override val viewModel: AlarmViewModel by viewModel()

    override fun invalidate(state: AlarmViewState) {
        state.alarmRequest.onFailure {
            snackbar(message = it.rationale)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAlarm.setOnClickListener {
            viewModel.raiseAlarm()
        }
    }
}
