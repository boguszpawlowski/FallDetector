package com.example.bpawlowski.falldetector.ui.main.contacts

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.ui.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.util.toast
import com.example.bpawlowski.falldetector.util.value
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_form.*
import javax.inject.Inject

class FormDialogFragment : DialogFragment() {

    private lateinit var parentViewModel: ContactsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: DialogFormBinding

    lateinit var viewModel: FormDialogViewModel

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_form, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FormDialogViewModel::class.java)
        parentViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()

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
        disposable.add(viewModel.checkIfIceExists().subscribe { exists -> //TODO remove if from here and replace toast with snackbar
            if (exists && contact.priority == UserPriority.PRIORITY_ICE) {
                requireContext().toast("Ice contact already exists")
            } else {
                parentViewModel.addContact(contact)
                dismiss()
            }
        })
    }
}