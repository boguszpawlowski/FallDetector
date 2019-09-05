package com.example.bpawlowski.falldetector.screens.main.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bpawlowski.core.domain.EventWrapper
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentContactDetailsBinding
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.screens.main.contacts.CODE_REQUEST_GALLERY
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.showBottomSheetDialog
import com.example.bpawlowski.falldetector.util.snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

const val IMAGE_TYPE = "image/*"
private const val CONTACT_ID = "contact_id"
private const val EMAIL_TYPE = "message/rfc822"

class ContactDetailsFragment : BaseFragment<FragmentContactDetailsBinding>() {

	override val layoutResID = R.layout.fragment_contact_details

	override val viewModel: ContactDetailsViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	private val screenStateObserver: Observer<ScreenState<Unit>> by lazy {
		Observer<ScreenState<Unit>> { state ->
			state.data?.let {
				snackbar(message = getString(R.string.snb_updated))
			}
			state.error?.let {
				snackbar(message = it.rationale)
			}
		}
	}

	private val photoDataObserver: Observer<EventWrapper<File>> by lazy {
		Observer<EventWrapper<File>> { event ->
			event.value?.let { file ->
				viewModel.updatePhotoPath(file)
			}
		}
	}

	/**
	 * On rotation we don't want to load data again, thus checking for saved instance state
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (savedInstanceState == null) {
			val contactId = arguments?.getLong(CONTACT_ID) ?: throw UnsupportedOperationException()
			viewModel.initData(contactId)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val contactId = arguments?.getLong(CONTACT_ID) ?: throw UnsupportedOperationException()
		initListeners(contactId)
		initObservers()
	}

	private fun initListeners(contactId: Long) = with(binding) {
		btnSave.setOnClickListener {
			this@ContactDetailsFragment.viewModel.updateContact(contactId)
			clearRootFocus()
		}
		btnReset.setOnClickListener {
			viewModel?.resetData()
			clearRootFocus()
		}
		imgProfile.setOnClickListener { openOptionsMenu() }
		btnCall.setOnClickListener { viewModel?.callContact(contactId, requireContext()) }
		btnSms.setOnClickListener { viewModel?.sendSms(contactId) }
		btnEmail.setOnClickListener { sendEmail() }
		fab.setOnClickListener {
			viewModel?.togglePriority()
			clearRootFocus()
		}
	}

	private fun initObservers() {
		sharedViewModel.capturedPhotoData.observe(viewLifecycleOwner, photoDataObserver)
		viewModel.screenStateData.observe(viewLifecycleOwner, screenStateObserver)
	}

	private fun sendEmail() {
		if (viewModel.contactForm.email.isNotBlank()) {
			with(Intent(Intent.ACTION_SEND)) {
				putExtra(Intent.EXTRA_EMAIL, arrayOf(viewModel.contactForm.email))
				type = EMAIL_TYPE
				startActivity(Intent.createChooser(this, getString(R.string.txt_email_chooser)))
			}
		} else {
			snackbar(message = getString(R.string.snb_email_warning))
		}
	}

	private fun openOptionsMenu() = showBottomSheetDialog(
		context = requireContext(),
		onItemSelectedListener = { index ->
			when (index) {
				0 -> openCamera()
				1 -> openGallery()
			}
		}
	)

	private fun openCamera() = checkPermission(
		activity = requireActivity(),
		permission = Manifest.permission.CAMERA,
		onGranted = {
			findNavController().navigate(
				ContactDetailsFragmentDirections.openCamera()
			)
		}
	)

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

		clearRootFocus()

		if (requestCode == CODE_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
			data?.data?.let {
				viewModel.contactForm.filePath = it.toString()
			}
		}
	}
}