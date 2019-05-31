package com.example.bpawlowski.falldetector.ui.main.contacts

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.ui.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.util.toast
import com.example.bpawlowski.falldetector.util.value
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_form.*
import javax.inject.Inject

class FormDialogFragment : BottomSheetDialogFragment() {

    private lateinit var parentViewModel: ContactsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: DialogFormBinding

    lateinit var viewModel: FormDialogViewModel

    private val disposable = CompositeDisposable()

    private var contactId: Long? = null

    override fun onCreateDialog(savedInstanceState: Bundle?) = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_form, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FormDialogViewModel::class.java)
        parentViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()

        arguments?.getLong(CONTACT_ID, -1).takeUnless { it == -1L }?.let {
            contactId = it
            disposable.add(
                viewModel.initEditingData(it).subscribe { contact ->
                    binding.txtContactName.setText(contact.name)
                    binding.txtContactMobile.setText(contact.mobile.toString())
                    binding.txtContactEmail.setText(contact.email.toString()) //TODO remove rx from binding
                    binding.cbxIce.isChecked = contact.priority == UserPriority.PRIORITY_ICE
                }
            )
        }

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