package com.example.bpawlowski.falldetector.ui.alarm

import android.os.Bundle
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.ui.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.databinding.ActivityAlarmBinding

class AlarmActivity : BaseActivity<AlarmViewModel, ActivityAlarmBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAlarm.setOnClickListener {
            viewModel.raiseAlarm()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun getViewModelClass() = AlarmViewModel::class.java

    override fun getLayoutID() = R.layout.activity_alarm
}
