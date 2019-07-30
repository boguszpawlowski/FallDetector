package com.example.bpawlowski.falldetector.ui.main.contacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bogusz.com.service.model.Contact
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentContactsBinding
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.base.recycler.DragToDismissCallback
import com.example.bpawlowski.falldetector.ui.base.recycler.ItemsAdapter
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.util.autoCleared
import com.example.bpawlowski.falldetector.util.setVisible
import com.example.bpawlowski.falldetector.util.snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val scrollThreshold = 20

class ContactsFragment : BaseFragment<FragmentContactsBinding>() {

	override val layoutID = R.layout.fragment_contacts

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
						onSelectListener = { showDetails(contact.id) },
						onCallClickListener = { viewModel.callContact(requireContext(), it) },
						onSmsClickListener = { viewModel.sendMessage(it) }
					)
				})
			}
		}
	}

	private val screenStateObserver: Observer<ScreenState<Contact>> by lazy {
		Observer<ScreenState<Contact>> { screenState ->
			screenState.onSuccess { contact ->
				snackbar(
					source = binding.root,
					message = getString(R.string.snackbar_deleted),
					actionListener = R.string.snackbar_undo,
					listener = { viewModel.addContact(contact) }
				)
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		adapter = ItemsAdapter()
		binding.recyclerContact.adapter = this@ContactsFragment.adapter
		ItemTouchHelper(DragToDismissCallback(requireContext())).attachToRecyclerView(binding.recyclerContact)

		initListeners()
		initObservers()
	}

	private fun initListeners() = with(binding) {
		fab.setOnClickListener { showDialog() }
		recyclerContact.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, verticalScroll: Int) {
				if (verticalScroll < -scrollThreshold) {
					fab.setVisible(true)
				} else if (verticalScroll > scrollThreshold) {
					fab.setVisible(false)
				}
			}
		})
	}

	private fun initObservers() {
		viewModel.contactsLiveData.reObserve(this, contactsObserver)
		viewModel.screenStateData.reObserve(this, screenStateObserver)
	}

	private fun showDialog() {
		findNavController().navigate(
			ContactsFragmentDirections.showDialog()
		)
	}

	private fun showDetails(id: Long? = null) {
		id?.let {
			findNavController().navigate(
				ContactsFragmentDirections.showDetails(id)
			)
		}
	}
}