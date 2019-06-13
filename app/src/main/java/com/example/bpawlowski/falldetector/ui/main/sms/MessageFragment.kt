package com.example.bpawlowski.falldetector.ui.main.sms

import android.os.Bundle
import androidx.lifecycle.Observer
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentMessageBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.recycler.ContactViewAdapter

class MessageFragment : BaseFragment<MessageViewModel, MainViewModel, FragmentMessageBinding>() {

    private var adapter: ContactViewAdapter? = null

    private val contactsObserver: Observer<List<Contact>> by lazy {
        Observer<List<Contact>> { contacts ->
            contacts?.let {
                adapter?.updateData(it.toMutableList())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ContactViewAdapter(
            viewType = R.layout.contact_item_sms,
            onClickListener = { viewModel.sendMessage(it) }
        )

        binding.recyclerContact.adapter = this@MessageFragment.adapter

        viewModel.contactsData.observe(this, contactsObserver)
    }

    override fun getViewModelClass() = MessageViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_message

    override fun getParentViewModeClass() = MainViewModel::class.java
}