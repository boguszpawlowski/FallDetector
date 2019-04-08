package com.example.bpawlowski.falldetector.activity.main.sms

import android.os.Bundle
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.activity.main.MainScreenState
import com.example.bpawlowski.falldetector.activity.main.MainViewModel
import com.example.bpawlowski.falldetector.activity.main.contacts.recycler.ContactViewAdapter
import com.example.bpawlowski.falldetector.databinding.FragmentMessageBinding

class MessageFragment : BaseFragment<MessageViewModel, MainViewModel, FragmentMessageBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerContact.apply {
            adapter = ContactViewAdapter(
                context = requireContext(),
                viewType = R.layout.contact_item_sms
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

    override fun getViewModelClass() = MessageViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_message

    override fun getParentViewModeClass() = MainViewModel::class.java

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }
}