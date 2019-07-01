package com.example.bpawlowski.falldetector.ui.main.contacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import bogusz.com.service.model.Contact
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentContactsBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemMoveCallBack
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemTouchHelperAdapter
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.recycler.ContactViewAdapter
import com.example.bpawlowski.falldetector.util.autoCleared

const val CONTACT_ID = "contact_id"

class ContactsFragment : BaseFragment<ContactsViewModel, MainViewModel, FragmentContactsBinding>() {

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
            onDismissListener = { viewModel.removeContact(it) },
            onSelectListener = { showDialog(it.id) }
        )

        binding.recyclerContact.adapter = this@ContactsFragment.adapter

        binding.fab.setOnClickListener { showDialog() }

        ItemTouchHelper(ItemMoveCallBack(requireContext(), adapter as ItemTouchHelperAdapter))
            .attachToRecyclerView(binding.recyclerContact)

        viewModel.contactsLiveData.reObserve(this, contactsObserver)
    }

    private fun showDialog(id: Long? = null) {
        val contactId = id ?: -1
        findNavController().navigate(
            ContactsFragmentDirections.showDialog(contactId)
        )
    }

    override fun getViewModelClass() = ContactsViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_contacts

    override fun getSharedViewModeClass() = MainViewModel::class.java
}