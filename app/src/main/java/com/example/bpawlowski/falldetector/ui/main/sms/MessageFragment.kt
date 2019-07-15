package com.example.bpawlowski.falldetector.ui.main.sms

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import bogusz.com.service.model.Contact
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentMessageBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemsAdapter
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.ContactsViewModel
import com.example.bpawlowski.falldetector.util.autoCleared
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageFragment : BaseFragment<MessageViewModel, MainViewModel, FragmentMessageBinding>() {

	override val viewModel: MessageViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

    private var adapter by autoCleared<ItemsAdapter>()

    private val contactsObserver: Observer<List<Contact>> by lazy {
        Observer<List<Contact>> { contacts ->
            contacts?.let {
                adapter.update(it.map { contact ->
                    MessageContactItem(contact) { viewModel.sendMessage(contact) }
                })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemsAdapter()
        binding.recyclerContact.adapter = this@MessageFragment.adapter
        viewModel.contactsData.reObserve(this, contactsObserver)
    }

    override fun getLayoutID() = R.layout.fragment_message
}