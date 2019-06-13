package com.example.bpawlowski.falldetector.ui.main.call

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.Observer
import bogusz.com.service.model.Contact
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentCallBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.recycler.ContactViewAdapter
import com.example.bpawlowski.falldetector.util.checkPermission

class CallFragment : BaseFragment<CallViewModel, MainViewModel, FragmentCallBinding>() {

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
            viewType = R.layout.contact_item_call,
            onClickListener = { contact ->
                checkPermission(
                    activity = requireActivity(),
                    permission = Manifest.permission.CALL_PHONE,
                    onGranted = { viewModel.callContact(requireContext(), contact) }
                )
            }
        )

        binding.recyclerContact.adapter = this@CallFragment.adapter

        viewModel.contactsLiveData.observe(this, contactsObserver)
    }

    override fun getViewModelClass() = CallViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_call

    override fun getParentViewModeClass() = MainViewModel::class.java
}
