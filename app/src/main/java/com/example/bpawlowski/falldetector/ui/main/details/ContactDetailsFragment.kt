package com.example.bpawlowski.falldetector.ui.main.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import bogusz.com.service.util.reObserve
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.FragmentContactDetailsBinding
import com.example.bpawlowski.falldetector.domain.ScreenState
import com.example.bpawlowski.falldetector.ui.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.CODE_REQUEST_GALLERY
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.setVisible
import com.example.bpawlowski.falldetector.util.snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CONTACT_ID = "contact_id"

class ContactDetailsFragment : BaseFragment<FragmentContactDetailsBinding>() {

	override val layoutID = R.layout.fragment_contact_details

	override val viewModel: ContactDetailsViewModel by viewModel()

	override val sharedViewModel: MainViewModel by sharedViewModel()

	private val screenStateObserver: Observer<ScreenState<Int>> by lazy {
		Observer<ScreenState<Int>> { state ->
			state.onSuccess {
				snackbar(binding.root, getString(R.string.snb_updated))
			}
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val contactId = arguments?.getLong(CONTACT_ID) ?: throw UnsupportedOperationException()
		viewModel.initData(contactId)
		initListeners(contactId)
		viewModel.screenStateData.reObserve(this, screenStateObserver)
	}

	private fun initListeners(contactId: Long) = with(binding) {
		btnSave.setOnClickListener { this@ContactDetailsFragment.viewModel.updateContact(contactId) }
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