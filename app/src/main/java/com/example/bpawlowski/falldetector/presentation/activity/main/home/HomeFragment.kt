package com.example.bpawlowski.falldetector.presentation.activity.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentHomeBinding
import com.example.bpawlowski.falldetector.presentation.activity.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.presentation.activity.main.MainViewModel


class HomeFragment : BaseFragment<HomeViewModel, MainViewModel, FragmentHomeBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getLayoutID(): Int = R.layout.fragment_home

    override fun getParentViewModeClass(): Class<MainViewModel> = MainViewModel::class.java

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = HomeFragment()
    }
}
