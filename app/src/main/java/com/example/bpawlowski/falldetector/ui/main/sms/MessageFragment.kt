package com.example.bpawlowski.falldetector.ui.main.sms

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import bogusz.com.service.model.Contact
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentMessageBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.recycler.ContactViewAdapter
import com.example.bpawlowski.falldetector.util.autoCleared

class MessageFragment : BaseFragment<MessageViewModel, MainViewModel, FragmentMessageBinding>() {

    private var adapter by autoCleared<ContactViewAdapter>()

    private val contactsObserver: Observer<List<Contact>> by lazy {
        Observer<List<Contact>> { contacts ->
            contacts?.let {
                adapter.updateData(it.toMutableList())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactViewAdapter(
            viewType = R.layout.contact_item_sms,
            onClickListener = { viewModel.sendMessage(it) }
        )

        binding.recyclerContact.adapter = this@MessageFragment.adapter

        viewModel.contactsData.reObserve(this, contactsObserver)
    }

    override fun getViewModelClass() = MessageViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_message

    override fun getSharedViewModeClass() = MainViewModel::class.java
}