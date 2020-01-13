package com.example.bpawlowski.falldetector.screens.main.home

import android.os.Bundle
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.buttonMonitor
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewState>() {

    override val layoutResID = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModel()

    override fun invalidate(state: HomeViewState) {
        // todo service error rationale
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonMonitor.setOnClickListener {
            viewModel.toggleService(requireContext())
        }
    }
}
