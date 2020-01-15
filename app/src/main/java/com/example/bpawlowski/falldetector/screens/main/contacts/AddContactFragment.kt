package com.example.bpawlowski.falldetector.screens.main.contacts

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.screens.main.camera.CameraViewModel
import com.example.bpawlowski.falldetector.screens.main.camera.CameraViewState
import com.example.bpawlowski.falldetector.screens.main.details.ContactDetailsFragmentDirections
import com.example.bpawlowski.falldetector.screens.main.details.IMAGE_TYPE
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.checkedChanges
import com.example.bpawlowski.falldetector.util.loadContactImage
import com.example.bpawlowski.falldetector.util.showBottomSheetDialog
import com.example.bpawlowski.falldetector.util.snackbar
import com.example.bpawlowski.falldetector.util.textChanges
import kotlinx.android.synthetic.main.fragment_add_contact.buttonApply
import kotlinx.android.synthetic.main.fragment_add_contact.buttonCancel
import kotlinx.android.synthetic.main.fragment_add_contact.checkboxIce
import kotlinx.android.synthetic.main.fragment_add_contact.imageProfile
import kotlinx.android.synthetic.main.fragment_add_contact.textEmail
import kotlinx.android.synthetic.main.fragment_add_contact.textMobile
import kotlinx.android.synthetic.main.fragment_add_contact.textName
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CODE_REQUEST_GALLERY = 9999

class AddContactFragment :
    BaseFragment<AddContactViewState>() {

    override val viewModel: AddContactViewModel by viewModel()

    private val cameraViewModel: CameraViewModel by sharedViewModel()

    override val layoutResID = R.layout.fragment_add_contact

    override fun invalidate(state: AddContactViewState) {
        with(state.contactForm) {
            buttonApply.isEnabled = isValid

            textName.error = nameError
            textMobile.error = mobileError
            textEmail.error = emailError

            filePath?.let {
                imageProfile.loadContactImage(requireContext(), it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        viewScope.launch {
            viewModel.subscribe(AddContactViewState::saveContactRequest) { result ->
                result.onSuccess {
                    findNavController().popBackStack()
                }.onFailure {
                    snackbar(it.rationale)
                }
            }
        }

        viewScope.launch {
            textEmail.textChanges().collect {
                viewModel.updateEmail(it)
            }
        }
        viewScope.launch {
            textMobile.textChanges().collect {
                viewModel.updatePhone(it)
            }
        }
        viewScope.launch {
            textName.textChanges().collect {
                viewModel.updateName(it)
            }
        }
        viewScope.launch {
            checkboxIce.checkedChanges().collect {
                viewModel.updatePriority(it)
            }
        }
        viewScope.launch {
            cameraViewModel.subscribeToAll(CameraViewState::photoPath) { photoPath ->
                photoPath?.let {
                    viewModel.updatePhotoPath(it)
                    cameraViewModel.resetPhotoPath() // todo think of better solution
                }
            }
        }
    }

    private fun initListeners() {
        buttonApply.setOnClickListener { this@AddContactFragment.viewModel.tryToAddContact() }
        buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        imageProfile.setOnClickListener { openOptionsMenu() }
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

        if (requestCode == CODE_REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                viewModel.updatePhotoPath(it.toString())
            }
        }
    }
}
