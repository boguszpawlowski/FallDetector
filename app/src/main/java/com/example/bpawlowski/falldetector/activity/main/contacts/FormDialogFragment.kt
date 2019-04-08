package com.example.bpawlowski.falldetector.activity.main.contacts

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.activity.base.activity.ViewModelFactory
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.util.value
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.dialog_form.*
import javax.inject.Inject

class FormDialogFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: DialogFormBinding

    lateinit var viewModel: FormDialogViewModel

    lateinit var parentViewModel: ContactsViewModel

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
            parentViewModel.addContact(contact)
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
    //TODO check ICE, maybe add base dialog fragment
}