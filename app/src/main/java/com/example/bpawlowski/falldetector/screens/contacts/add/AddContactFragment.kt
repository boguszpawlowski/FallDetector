package com.example.bpawlowski.falldetector.screens.contacts.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentAddContactBinding
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.screens.camera.CameraViewModel
import com.example.bpawlowski.falldetector.screens.camera.CameraViewState
import com.example.bpawlowski.falldetector.screens.contacts.add.compose.AddContactScreen
import com.example.bpawlowski.falldetector.screens.contacts.details.ContactDetailsFragmentDirections
import com.example.bpawlowski.falldetector.screens.contacts.details.IMAGE_TYPE
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.showBottomSheetDialog
import com.example.bpawlowski.falldetector.util.snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CODE_REQUEST_GALLERY = 9999

class AddContactFragment : BaseFragment<AddContactViewState>() {

    override val viewModel: AddContactViewModel by viewModel()

    private val cameraViewModel: CameraViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddContactBinding.inflate(inflater, container, false)

        binding.composeView.setContent {
            AddContactScreen(
                onContactAdded = viewModel::addContact,
                onCancel = { findNavController().popBackStack() }
            )
        }

        return binding.root
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
            cameraViewModel.subscribeToAll(CameraViewState::photoPath) { photoPath ->
                photoPath?.let {
//                    viewModel.updatePhotoPath(it)
                    cameraViewModel.resetPhotoPath() // todo think of better solution
                }
            }
        }
    }

    private fun initListeners() {
//        buttonApply.setOnClickListener { this@AddContactFragment.viewModel.tryToAddContact() }
//        buttonCancel.setOnClickListener {
//            findNavController().popBackStack()
//        }
//        imageProfile.setOnClickListener { openOptionsMenu() }
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
//                viewModel.updatePhotoPath(it.toString())
            }
        }
    }
}
