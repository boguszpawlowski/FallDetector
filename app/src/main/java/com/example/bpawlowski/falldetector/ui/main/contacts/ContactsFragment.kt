package com.example.bpawlowski.falldetector.ui.main.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
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

const val CONTACT_ID = "contact_id"
const val TRANSITION_NAME = "transition_name"

class ContactsFragment : BaseFragment<ContactsViewModel, MainViewModel, FragmentContactsBinding>() {

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
            onDismissListener = { viewModel.removeContact(it) },
            onSelectListener = {  contact, position -> showDialog(contact.id, "tr_$position") }
        )

        binding.recyclerContact.adapter = this@ContactsFragment.adapter

        binding.fab.setOnClickListener { showDialog() }

        ItemTouchHelper(ItemMoveCallBack(requireContext(), adapter as ItemTouchHelperAdapter))
            .attachToRecyclerView(binding.recyclerContact)

        viewModel.contactsLiveData.reObserve(this, contactsObserver)
    }

    private fun showDialog(ticketId: Long? = null, transitionName: String = "", imageView: View? = null) {
        val args = Bundle()
        ticketId?.let {
            args.putLong(CONTACT_ID, it)
            args.putString(TRANSITION_NAME, transitionName)
        }

        val fragmentTransaction = childFragmentManager.beginTransaction()

        imageView?.let {
            fragmentTransaction.addSharedElement(imageView, transitionName)
        }

        val fragment = childFragmentManager.fragmentFactory.instantiate(
            ClassLoader.getSystemClassLoader(),
            FormDialogFragment::class.java.name
        ).apply { arguments = args } as DialogFragment

        fragment.show(fragmentTransaction, "FormDialogFragment")
    }

    override fun getViewModelClass() = ContactsViewModel::class.java

    override fun getLayoutID() = R.layout.fragment_contacts

    override fun getParentViewModeClass() = MainViewModel::class.java
}