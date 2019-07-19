package com.example.bpawlowski.falldetector.ui.main.contacts

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import bogusz.com.service.database.FallDetectorResult
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.domain.ErrorNotification
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.util.autoCleared
import com.example.bpawlowski.falldetector.util.checkPermission
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CODE_REQUEST_GALLERY = 9999
private const val CONTACT_ID = "contact_id"

class FormDialogFragment : DialogFragment() {

	private var binding by autoCleared<DialogFormBinding>()

	private val viewModel: FormDialogViewModel by viewModel()

	private val sharedViewModel: MainViewModel by sharedViewModel()

	private val resultObserver: Observer<FallDetectorResult<Long>> by lazy {
		Observer<FallDetectorResult<Long>> { result ->
			result.onSuccess {
				dismiss()
			}.onKnownFailure {
				sharedViewModel.handleKnownError(ErrorNotification(binding.root, it.rationale))
			}
		}
	}

	override fun getTheme() = R.style.FormDialogStyle

	override fun onCreateDialog(savedInstanceState: Bundle?) = Dialog(requireContext(), theme)

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
		imgProfile.setOnClickListener { openGallery() }
	}

	private fun openGallery() = checkPermission(
		activity = requireActivity(),
		permission = Manifest.permission.READ_EXTERNAL_STORAGE,
		onGranted = {
			with(Intent(Intent.ACTION_OPEN_DOCUMENT)) {
				type = "image/*"
				startActivityForResult(this, CODE_REQUEST_GALLERY)
			}
		})

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == CODE_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
			data?.data?.let {
				viewModel.contactForm.filePath = it.toString()
			}
		}
	}
}