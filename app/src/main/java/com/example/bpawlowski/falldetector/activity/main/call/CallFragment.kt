package com.example.bpawlowski.falldetector.activity.main.call

import android.Manifest
import android.os.Bundle
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.activity.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.activity.main.MainScreenState
import com.example.bpawlowski.falldetector.activity.main.MainViewModel
import com.example.bpawlowski.falldetector.activity.main.contacts.recycler.ContactViewAdapter
import com.example.bpawlowski.falldetector.databinding.FragmentCallBinding
import com.example.bpawlowski.falldetector.util.checkPermission

class CallFragment : BaseFragment<CallViewModel, MainViewModel, FragmentCallBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerContact.apply {
            adapter = ContactViewAdapter(
                context = requireContext(),
                viewType = R.layout.contact_item_call,
                onClickListener = { contact ->
                    checkPermission(
                        activity = requireActivity(),
                        permission = Manifest.permission.CALL_PHONE,
                        onGranted = { viewModel.callContact(requireContext(), contact) }
                    )
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()

        disposable.add(
            viewModel.contactsSubject
                .subscribe(
                    { (binding.recyclerContact.adapter as? ContactViewAdapter)?.updateData(it.toMutableList()) },
                    { parentViewModel.changeState(MainScreenState.ErrorState(it)) }
                )

        )
    }

    override fun getViewModelClass() = CallViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_call

    override fun getParentViewModeClass() = MainViewModel::class.java
}
