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
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.di.Injectable
import com.example.bpawlowski.falldetector.ui.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.util.autoCleared
import com.example.bpawlowski.falldetector.util.toast
import javax.inject.Inject

class FormDialogFragment : DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var binding by autoCleared<DialogFormBinding>()

    lateinit var viewModel: FormDialogViewModel

    private val resultObserver: Observer<FallDetectorResult<Long>> by lazy {
        Observer<FallDetectorResult<Long>> { result ->
            result.onSuccess {
                dismiss()
            }.onKnownFailure {
                requireContext().toast(it.rationale) //todo pass failure to mainViewModel, event bus??
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = Dialog(requireContext(), theme)

    override fun getTheme() = R.style.FormDialogStyle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<DialogFormBinding>(inflater, R.layout.dialog_form, container, false)
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FormDialogViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.addContactResultData.reObserve(this, resultObserver)

        val contactId = arguments?.getLong(CONTACT_ID).takeUnless { it == -1L }
        viewModel.initData(contactId)
        initListeners(contactId)
    }

    private fun initListeners(contactId: Long?) = with(binding) {
        btnApply.setOnClickListener { this@FormDialogFragment.viewModel.tryToAddContact(contactId) }
        btnCancel.setOnClickListener { dismiss() }
    }
}