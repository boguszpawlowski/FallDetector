@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.screens.contacts.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.base.fragment.withState
import com.example.bpawlowski.falldetector.databinding.FragmentContactDetailsBinding
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.screens.camera.CameraViewModel
import com.example.bpawlowski.falldetector.screens.camera.CameraViewState
import com.example.bpawlowski.falldetector.screens.contacts.add.CODE_REQUEST_GALLERY
import com.example.bpawlowski.falldetector.screens.contacts.details.compose.ProfileScreen
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.showBottomSheetDialog
import com.example.bpawlowski.falldetector.util.snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val IMAGE_TYPE = "image/*"
private const val EMAIL_TYPE = "message/rfc822"

class ContactDetailsFragment : BaseFragment<ContactDetailsViewState>() {

    override val viewModel: ContactDetailsViewModel by viewModel()

    private val cameraViewModel: CameraViewModel by sharedViewModel()

    private val args: ContactDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactDetailsBinding.inflate(inflater, container, false)

        binding.profilePicture.setContent {
            val state = viewModel.stateFlow.collectAsState()

            state.value.contact.invoke()?.let {
                ProfileScreen(
                    it,
                    onContactEdited = { newContact ->
                        viewModel.updateContact(newContact)
                    },
                    onCallClicked = viewModel::callContact,
                    onEmailClicked = ::sendEmail,
                    onTextMessageClicked = viewModel::sendSms
                )
            }
        }
        return binding.root
    }

    /**
     * On rotation we don't want to load data again, thus checking for saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.initData(args.contactId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewScope.launch {
            viewModel.subscribe(ContactDetailsViewState::saveContactRequest) { result ->
                result.onSuccess {
                    snackbar(message = getString(R.string.snb_updated))
                }.onFailure {
                    snackbar(message = it.rationale)
                }
            }
        }

        initListeners(args.contactId)
    }

    private fun initListeners(contactId: Long) {
        viewScope.launch {
            cameraViewModel.subscribeToAll(CameraViewState::photoPath) { photoPath ->
                photoPath?.let {
                    cameraViewModel.resetPhotoPath() // todo think of better solution
                }
            }
        }
    }

    private fun sendEmail() = withState { state ->
        val contact = state.contact() ?: return@withState
        if (contact.email.isNullOrBlank().not()) {
            with(Intent(Intent.ACTION_SEND)) {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(contact.email))
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
                viewModel.updatePhotoPath(it.toString())
            }
        }
    }
}
