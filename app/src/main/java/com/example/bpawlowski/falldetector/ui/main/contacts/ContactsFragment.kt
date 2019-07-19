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
import com.example.bpawlowski.falldetector.ui.base.recycler.DragToDismissCallback
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemsAdapter
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.util.autoCleared
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CONTACT_ID = "contact_id"
const val CODE_REQUEST_GALLERY = 9999

class ContactsFragment : BaseFragment<ContactsViewModel, MainViewModel, FragmentContactsBinding>() {

	override val viewModel: ContactsViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	private var adapter by autoCleared<ItemsAdapter>()

	private val contactsObserver: Observer<List<Contact>> by lazy {
		Observer<List<Contact>> { contacts ->
			contacts?.let {
				adapter.update(it.map { contact ->
					ContactItem(
						data = contact,
						onDismissListener = { viewModel.removeContact(contact) },
						onSelectListener = { showDialog(contact.id) },
						onCallClickListener = { viewModel.callContact(requireContext(), it) },
						onSmsClickListener = { viewModel.sendMessage(it) }
					)
				})
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		adapter = ItemsAdapter()

		binding.recyclerContact.adapter = this@ContactsFragment.adapter
		binding.fab.setOnClickListener { showDialog() }

		ItemTouchHelper(DragToDismissCallback(requireContext())).attachToRecyclerView(binding.recyclerContact)

		viewModel.contactsLiveData.reObserve(this, contactsObserver)
	}

	private fun showDialog(id: Long? = null) {
		findNavController().navigate(
			ContactsFragmentDirections.showDialog(id ?: -1L)
		)
	}

	override fun getLayoutID() = R.layout.fragment_contacts
}