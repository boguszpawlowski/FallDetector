package com.example.bpawlowski.falldetector.ui.main.home

import android.os.Bundle
import android.view.View
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentHomeBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

	override val layoutID = R.layout.fragment_home

	override val viewModel: HomeViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMonitor.setOnClickListener {
            viewModel.toggleService(requireContext())
        }
    }
}
