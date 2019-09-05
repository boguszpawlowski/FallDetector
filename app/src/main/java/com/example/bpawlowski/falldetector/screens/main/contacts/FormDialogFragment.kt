package com.example.bpawlowski.falldetector.screens.main.contacts

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
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.DialogFormBinding
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.screens.main.details.IMAGE_TYPE
import com.example.bpawlowski.falldetector.util.autoCleared
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CODE_REQUEST_GALLERY = 9999

class FormDialogFragment : DialogFragment() { //todo probably change this to normal fragment/bottom sheet

	private var binding by autoCleared<DialogFormBinding>()

	private val viewModel: FormDialogViewModel by viewModel()

	private val screenStateObserver: Observer<ScreenState<Long>> by lazy {
		Observer<ScreenState<Long>> { state ->
			state.data?.let {
				dismiss()
			}
			state.error?.let {
				toast(it.rationale)
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

		viewModel.screenStateData.observe(viewLifecycleOwner, screenStateObserver)

		initListeners()
	}

	private fun initListeners() = with(binding) {
		btnApply.setOnClickListener { this@FormDialogFragment.viewModel.tryToAddContact() }
		btnCancel.setOnClickListener { dismiss() }
		imgProfile.setOnClickListener { openGallery() }
	}

	private fun openGallery() = checkPermission(
		activity = requireActivity(),
		permission = Manifest.permission.READ_EXTERNAL_STORAGE,
		onGranted = {
			with(Intent(Intent.ACTION_OPEN_DOCUMENT)) {
				type = IMAGE_TYPE
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