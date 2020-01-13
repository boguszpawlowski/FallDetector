@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.screens.main.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.base.fragment.withState
import com.example.bpawlowski.falldetector.domain.onFailure
import com.example.bpawlowski.falldetector.domain.onSuccess
import com.example.bpawlowski.falldetector.screens.main.camera.CameraViewModel
import com.example.bpawlowski.falldetector.screens.main.camera.CameraViewState
import com.example.bpawlowski.falldetector.screens.main.contacts.CODE_REQUEST_GALLERY
import com.example.bpawlowski.falldetector.util.checkPermission
import com.example.bpawlowski.falldetector.util.loadContactImage
import com.example.bpawlowski.falldetector.util.setVisible
import com.example.bpawlowski.falldetector.util.showBottomSheetDialog
import com.example.bpawlowski.falldetector.util.snackbar
import com.example.bpawlowski.falldetector.util.textChanges
import kotlinx.android.synthetic.main.fragment_contact_details.buttonCall
import kotlinx.android.synthetic.main.fragment_contact_details.buttonEmail
import kotlinx.android.synthetic.main.fragment_contact_details.buttonReset
import kotlinx.android.synthetic.main.fragment_contact_details.buttonSave
import kotlinx.android.synthetic.main.fragment_contact_details.buttonSms
import kotlinx.android.synthetic.main.fragment_contact_details.fabPriority
import kotlinx.android.synthetic.main.fragment_contact_details.imageProfile
import kotlinx.android.synthetic.main.fragment_contact_details.textEmail
import kotlinx.android.synthetic.main.fragment_contact_details.textName
import kotlinx.android.synthetic.main.fragment_contact_details.textPhone
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val IMAGE_TYPE = "image/*"
private const val EMAIL_TYPE = "message/rfc822"

// todo take photo option
class ContactDetailsFragment : BaseFragment<ContactDetailsViewState>() {

    override val layoutResID = R.layout.fragment_contact_details

    override val viewModel: ContactDetailsViewModel by viewModel()

    private val cameraViewModel: CameraViewModel by sharedViewModel()

    private val args: ContactDetailsFragmentArgs by navArgs()

    override fun invalidate(state: ContactDetailsViewState) {
        val contactChanged = state.contactForm.hasChanged(state.contact())

        buttonReset.setVisible(contactChanged)
        buttonSave.setVisible(contactChanged)
        buttonSave.isEnabled = state.contactForm.isValid

        state.contactForm.filePath.let {
            imageProfile.loadContactImage(requireContext(), it)
        }

        textEmail.error = state.contactForm.emailError
        textPhone.error = state.contactForm.mobileError

        fabPriority.setImageResource(
            if (state.contactForm.priority) {
                R.drawable.ic_star_black_24dp
            } else {
                R.drawable.ic_star_border_black_24dp
            }
        )
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

        reloadForm()
        initListeners(args.contactId)
    }

    private fun reloadForm() {
        viewScope.launch {
            viewModel.subscribeOnce(ContactDetailsViewState::contact) { contact ->
                contact()?.let {
                    textName.setText(it.name)
                    textPhone.setText(it.mobile.toString())
                    textEmail.setText(it.email)
                }
            }
        }
    }

    private fun initListeners(contactId: Long) {
        buttonSave.setOnClickListener { viewModel.updateContact(contactId) }
        imageProfile.setOnClickListener { openOptionsMenu() }
        buttonCall.setOnClickListener { viewModel.callContact(contactId) }
        buttonSms.setOnClickListener { viewModel.sendSms(contactId) }
        buttonEmail.setOnClickListener { sendEmail() }
        fabPriority.setOnClickListener { viewModel.togglePriority() }
        buttonReset.setOnClickListener {
            viewModel.resetData()
            reloadForm()
        }

        textEmail.textChanges().onEach {
            viewModel.updateEmail(it)
        }.launchIn(viewScope)

        textPhone.textChanges().onEach {
            viewModel.updatePhone(it)
        }.launchIn(viewScope)

        textName.textChanges().onEach {
            viewModel.updateName(it)
        }.launchIn(viewScope)

        viewScope.launch {
            cameraViewModel.subscribeToAll(CameraViewState::photoPath) { photoPath ->
                photoPath?.let {
                    viewModel.updatePhotoPath(it)
                    cameraViewModel.resetPhotoPath() // todo think of better solution
                }
            }
        }
    }

    private fun sendEmail() = withState { state ->
        val contactForm = state.contactForm
        if (contactForm.email.isNotBlank()) {
            with(Intent(Intent.ACTION_SEND)) {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(contactForm.email))
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
