package com.example.bpawlowski.falldetector.ui.main.call

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import bogusz.com.service.model.Contact
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentCallBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemAdapter
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemsAdapter
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.util.autoCleared
import com.example.bpawlowski.falldetector.util.checkPermission

class CallFragment : BaseFragment<CallViewModel, MainViewModel, FragmentCallBinding>() {

    private var adapter by autoCleared<ItemAdapter>()

    private val contactsObserver: Observer<List<Contact>> by lazy {
        Observer<List<Contact>> { contacts ->
            contacts?.let {
                adapter.update(it.map { contact ->
                    CallContactItem(contact) { tryToCall(contact) }
                })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemsAdapter()
        binding.recyclerContact.adapter = this@CallFragment.adapter
        viewModel.contactsLiveData.reObserve(this, contactsObserver)
    }

    private fun tryToCall(contact: Contact) {
        checkPermission(
            activity = requireActivity(),
            permission = Manifest.permission.CALL_PHONE,
            onGranted = { viewModel.callContact(requireContext(), contact) }
        )
    }

    override fun getViewModelClass() = CallViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_call

    override fun getSharedViewModeClass() = MainViewModel::class.java
}
