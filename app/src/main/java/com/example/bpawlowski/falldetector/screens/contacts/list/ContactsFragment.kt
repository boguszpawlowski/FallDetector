package com.example.bpawlowski.falldetector.screens.contacts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentContactsBinding
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.screens.contacts.list.compose.ContactsScreen
import com.example.bpawlowski.falldetector.util.snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : BaseFragment<ContactsViewState>() {

    override val viewModel: ContactsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactsBinding.inflate(inflater, container, false)

        binding.composeView.setContent {
            ContactsScreen(
                viewState = viewModel.stateFlow,
                onCallClicked = viewModel::callContact,
                onTextMessageClicked = viewModel::sendMessage,
                onItemClicked = ::showDetails,
                onAddContactClicked = ::goToAddContact,
                onContactDismissed = viewModel::removeContact
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        viewScope.launch {
            viewModel.subscribe(ContactsViewState::removeContactRequest) {
                it.onSuccess { contact ->
                    snackbar(
                        message = getString(R.string.snackbar_deleted),
                        actionResId = R.string.snackbar_undo,
                        actionListener = { viewModel.addContact(contact) }
                    )
                }.onFailure { exception ->
                    snackbar(message = exception.rationale)
                }
            }
        }
    }

    private fun goToAddContact() = findNavController().navigate(
        ContactsFragmentDirections.showAddContact()
    )

    private fun showDetails(contact: Contact) {
        val contactId = contact.id ?: error("Id shouldn't be null")

        findNavController().navigate(
            ContactsFragmentDirections.showDetails(contactId)
        )
    }
}
