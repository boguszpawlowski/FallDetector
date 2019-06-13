package com.example.bpawlowski.falldetector.ui.main.contacts

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import bogusz.com.service.util.doNothing
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.di.Injectable
import com.example.bpawlowski.falldetector.ui.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.util.toast
import com.example.bpawlowski.falldetector.util.value
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.dialog_form.*
import javax.inject.Inject

class FormDialogFragment : DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: DialogFormBinding

    lateinit var viewModel: FormDialogViewModel

    private val disposable = CompositeDisposable()

    private var contactId: Long? = null

    override fun onCreateDialog(savedInstanceState: Bundle?) = Dialog(requireContext(), theme)

    override fun getTheme() = R.style.FormDialogStyle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_form, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FormDialogViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()

        val id = arguments?.getLong(CONTACT_ID, -1) ?: -1

        disposable.add(
            viewModel.initEditingData(id).subscribeBy(
                onSuccess = { contact ->
                    val (_, name, mobile, email, priority) = contact
                    with(binding) {
                        txtContactName.setText(name)
                        txtContactMobile.setText(mobile.toString())
                        txtContactEmail.setText(email)
                        cbxIce.isChecked = priority == UserPriority.PRIORITY_ICE
                        imgProfile.transitionName = arguments?.getString(TRANSITION_NAME)
                    }
                    contactId = id
                },
                onError = { doNothing }
            ))

        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.dispose()
    }

    private fun initListeners() {
        binding.btnApply.setOnClickListener {
            val name = binding.txtContactName.value
            val email = binding.txtContactEmail.value
            val mobile = binding.txtContactMobile.value.toInt()
            val priority = if (cbx_ice.isChecked) UserPriority.PRIORITY_ICE else UserPriority.PRIORITY_NORMAL

            val contact = Contact(
                id = contactId,
                name = name,
                mobile = mobile,
                email = email,
                priority = priority
            )
            tryToAddContact(contact)
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun tryToAddContact(contact: Contact) {
        disposable.add(viewModel.checkIfIceExists().flatMapCompletable { exists ->
            if (exists && contact.priority == UserPriority.PRIORITY_ICE) {
                Completable.error(Throwable("Ice already exists"))
            } else {
                viewModel.addContact(contact)
            }
        }.subscribeBy(
            onComplete = { dismiss() },
            onError = { context?.toast("Ice Contact already exists") }
        ))
    }
}