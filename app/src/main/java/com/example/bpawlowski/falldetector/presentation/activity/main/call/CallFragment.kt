package com.example.bpawlowski.falldetector.presentation.activity.main.call

import android.os.Bundle
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentCallBinding
import com.example.bpawlowski.falldetector.presentation.activity.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.presentation.activity.main.MainScreenState
import com.example.bpawlowski.falldetector.presentation.activity.main.MainViewModel
import com.example.bpawlowski.falldetector.presentation.activity.main.contacts.recycler.ContactViewAdapter

class CallFragment : BaseFragment<CallViewModel, MainViewModel, FragmentCallBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerContact.apply {
            adapter = ContactViewAdapter(
                context = requireContext(),
                viewType = R.layout.contact_item_call
            )
        }
    }

    override fun onResume() {
        super.onResume()

        disposable.add(
            viewModel.contactsSubject
                .subscribe(
                    { (binding.recyclerContact.adapter as? ContactViewAdapter)?.data = it.toMutableList() },
                    { parentViewModel.changeState(MainScreenState.ErrorState(it)) }
                )

        )
    }

    override fun getViewModelClass() = CallViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_call

    override fun getParentViewModeClass() = MainViewModel::class.java

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }
}