package com.example.bpawlowski.falldetector.presentation.activity.main.contacts

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
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.presentation.util.value
import com.example.bpawlowski.falldetector.service.model.Contact
import com.example.bpawlowski.falldetector.service.model.UserPriority
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.dialog_form.*
import kotlinx.android.synthetic.main.dialog_form.view.*
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
    ): View? { //TODO fucking blur background
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_form, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        with(dialog.window) {
            this?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this?.attributes?.windowAnimations = R.style.FormDialogAnimation
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FormDialogViewModel::class.java)
        parentViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        initListeners()
        viewModel.onResume()
    }

    private fun initListeners() {
        binding.root.btn_apply.setOnClickListener {
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
        binding.root.btn_cancel.setOnClickListener {
            dismiss()
        }
    }
    //TODO check ICE, maybe add base dialog fragment
}