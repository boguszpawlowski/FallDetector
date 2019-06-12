package com.example.bpawlowski.falldetector.ui.main.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentContactsBinding
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemMoveCallBack
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemTouchHelperAdapter
import com.example.bpawlowski.falldetector.ui.main.MainScreenState
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.recycler.ContactViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers

const val CONTACT_ID = "contact_id"

class ContactsFragment : BaseFragment<ContactsViewModel, MainViewModel, FragmentContactsBinding>() {

    private lateinit var touchHelper: ItemTouchHelper

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.recyclerContact.apply {
            adapter = ContactViewAdapter(
                onDismissListener = { viewModel.removeContact(it) },
                onSelectListener = { showDialog(it.id) }
            )
        }

        binding.fab.setOnClickListener { showDialog() }

        touchHelper = ItemTouchHelper(
            ItemMoveCallBack(
                requireContext(),
                (binding.recyclerContact.adapter as ItemTouchHelperAdapter)
            )
        )
        touchHelper.attachToRecyclerView(binding.recyclerContact)
    }

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

    private fun showDialog(ticketId: Long? = null) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val args = Bundle()
        ticketId?.let { args.putLong(CONTACT_ID, it) }

        val fragment = Fragment.instantiate(
            requireContext(),
            FormDialogFragment::class.java.canonicalName,
            args
        ) as FormDialogFragment
        fragment.show(fragmentTransaction, "FormDialogFragment")
    }

    override fun getViewModelClass() = ContactsViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_contacts

    override fun getParentViewModeClass() = MainViewModel::class.java
}