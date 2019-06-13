package com.example.bpawlowski.falldetector.ui.main.contacts

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.di.Injectable
import com.example.bpawlowski.falldetector.ui.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.util.toast
import com.example.bpawlowski.falldetector.util.value
import kotlinx.android.synthetic.main.dialog_form.*
import javax.inject.Inject

class FormDialogFragment : DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: DialogFormBinding

    lateinit var viewModel: FormDialogViewModel

    private val resultObserver: Observer<Boolean> by lazy {
        Observer<Boolean> { isSuccessful ->
            isSuccessful?.let {
                if (isSuccessful) {
                    dismiss()
                } else {
                    requireContext().toast("Ice contact already exists")
                }
            }
        }
    }

    private val initialDataObserver: Observer<Contact> by lazy {
        Observer<Contact> { contact ->
            contact?.let {
                val (_, name, mobile, email, priority) = it
                with(binding) {
                    txtContactName.setText(name)
                    txtContactMobile.setText(mobile.toString())
                    txtContactEmail.setText(email)
                    cbxIce.isChecked = priority == UserPriority.PRIORITY_ICE  //fixme
                }
            }
        }
    }

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

        viewModel.addContactResultData.observe(this, resultObserver)
        viewModel.initialContactData.observe(this, initialDataObserver)
    }

    override fun onResume() {
        super.onResume()

        val contactId = (arguments?.getLong(CONTACT_ID, -1) ?: -1).also {
            if (it != -1L) contactId = it
        }
        viewModel.initEditingData(contactId)
        initListeners()
    }

    private fun initListeners() {
        binding.btnApply.setOnClickListener {

            val contact = Contact(
                id = contactId,
                name = binding.txtContactName.value,
                mobile = binding.txtContactMobile.value.toInt(),
                email = binding.txtContactEmail.value,
                priority = if (cbx_ice.isChecked) UserPriority.PRIORITY_ICE else UserPriority.PRIORITY_NORMAL
            )
            viewModel.tryToAddContact(contact)
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}