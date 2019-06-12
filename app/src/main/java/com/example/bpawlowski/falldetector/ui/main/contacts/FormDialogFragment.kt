package com.example.bpawlowski.falldetector.ui.main.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.dialog_form.*
import javax.inject.Inject

class FormDialogFragment : BottomSheetDialogFragment(), Injectable { //TODO refactor

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: DialogFormBinding

    lateinit var viewModel: FormDialogViewModel

    private lateinit var parentViewModel: ContactsViewModel

    private val disposable = CompositeDisposable()

    private var contactId: Long? = null

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        BottomSheetDialog(requireContext(), theme)//TODO remove bottom sheet - add transition

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_form, container, false)

        binding.lifecycleOwner = this

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FormDialogViewModel::class.java)
        parentViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()

        val contactId = arguments?.getLong(CONTACT_ID, -1) ?: -1

        disposable.add(
            viewModel.initEditingData(contactId).subscribeBy(
                onSuccess = { contact ->
                    val (_, name, mobile, email, priority) = contact
                    with(binding) {
                        txtContactName.setText(name)
                        txtContactMobile.setText(mobile.toString())
                        txtContactEmail.setText(email)
                        cbxIce.isChecked = priority == UserPriority.PRIORITY_ICE  //fixme
                    }
                },
                onError = { doNothing }
            ))

        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.dispose()
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme

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

    @SuppressLint("CheckResult")
    private fun tryToAddContact(contact: Contact) {
        disposable.add(viewModel.checkIfIceExists().subscribe { exists ->
            //TODO remove if from here and replace toast with snackbar
            if (exists && contact.priority == UserPriority.PRIORITY_ICE) {
                requireContext().toast("Ice contact already exists")
            } else {
                parentViewModel.addContact(contact)
                dismiss()
            }
        })
    }
}