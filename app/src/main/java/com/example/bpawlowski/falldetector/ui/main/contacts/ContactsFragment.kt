package com.example.bpawlowski.falldetector.ui.main.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemMoveCallBack
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemTouchHelperAdapter
import com.example.bpawlowski.falldetector.ui.main.MainScreenState
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.recycler.ContactViewAdapter
import com.example.bpawlowski.falldetector.databinding.FragmentContactsBinding
import io.reactivex.android.schedulers.AndroidSchedulers

class ContactsFragment : BaseFragment<ContactsViewModel, MainViewModel, FragmentContactsBinding>() {

    lateinit var touchHelper: ItemTouchHelper

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerContact.apply {
            adapter = ContactViewAdapter(
                context = requireContext(),
                onDismissListener = { viewModel.removeContact(it) }
            )
        }

        binding.fab.setOnClickListener {
            val fragmentTransaction = childFragmentManager.beginTransaction()
            with(FormDialogFragment()) {
                show(fragmentTransaction, "FormDialogFragment")
            }
        }

        touchHelper = ItemTouchHelper(
            ItemMoveCallBack(
                requireContext(),
                (binding.recyclerContact.adapter as ItemTouchHelperAdapter)
            )
        )
        touchHelper.attachToRecyclerView(binding.recyclerContact)
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()

        disposable.add(
            viewModel.contactSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { (binding.recyclerContact.adapter as? ContactViewAdapter)?.updateData(it.toMutableList()) },
                    { parentViewModel.changeState(MainScreenState.ErrorState(it)) }
                )
        )
    }

    override fun getViewModelClass() = ContactsViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_contacts

    override fun getParentViewModeClass() = MainViewModel::class.java
}