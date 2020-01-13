package com.example.bpawlowski.falldetector.screens.main.contacts

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.base.fragment.simpleController
import com.example.bpawlowski.falldetector.base.recycler.SwipeCallback
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.util.autoClearedLazy
import com.example.bpawlowski.falldetector.util.setVisible
import com.example.bpawlowski.falldetector.util.snackbar
import kotlinx.android.synthetic.main.fragment_contacts.fabPriority
import kotlinx.android.synthetic.main.fragment_contacts.recyclerContact
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SCROLL_THRESHOLD = 20

class ContactsFragment : BaseFragment<ContactsViewState>() {

    override val layoutResID = R.layout.fragment_contacts

    override val viewModel: ContactsViewModel by viewModel()

    private val epoxyController: EpoxyController by autoClearedLazy {
        simpleController { state ->
            state.contacts.onSuccess {
                it.forEach {
                    contactView {
                        id(it.id)
                        contact(it)
                        onItemClick { _ -> showDetails(it.id) }
                        onCallClicked { _ -> viewModel.callContact(it) }
                        onSmsClicked { _ -> viewModel.sendMessage(it) }
                    }
                }
            }
        }
    }

    override fun invalidate(state: ContactsViewState) = epoxyController.requestModelBuild()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerContact.setController(epoxyController)
        EpoxyTouchHelper.initSwiping(recyclerContact)
            .leftAndRight()
            .withTarget(ContactViewModel_::class.java)
            .andCallbacks(object : SwipeCallback<ContactViewModel_>(requireContext()) {
                override fun onSwipeCompleted(
                    model: ContactViewModel_,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    model.contact().id?.let {
                        viewModel.removeContact(it)
                    }
                }
            })

        initListeners()
    }

    private fun initListeners() {
        fabPriority.setOnClickListener { showDialog() }
        recyclerContact.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, verticalScroll: Int) {
                if (verticalScroll < -SCROLL_THRESHOLD) {
                    fabPriority.setVisible(true)
                } else if (verticalScroll > SCROLL_THRESHOLD) {
                    fabPriority.setVisible(false)
                }
            }
        })

        viewScope.launch {
            viewModel.subscribe(ContactsViewState::removeContactRequest) {
                it.onSuccess { contact ->
                    snackbar(
                        message = getString(R.string.snackbar_deleted),
                        actionResId = R.string.snackbar_undo,
                        actionListener = {
                            viewModel.addContact(contact)
                        }
                    )
                }.onFailure { exception ->
                    snackbar(message = exception.rationale)
                }
            }
        }
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
