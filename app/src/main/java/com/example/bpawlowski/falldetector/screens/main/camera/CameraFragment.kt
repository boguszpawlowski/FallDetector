package com.example.bpawlowski.falldetector.screens.main.camera

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Rational
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraX
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureConfig
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.camera.view.TextureViewMeteringPointFactory
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.util.postDelayed
import com.example.bpawlowski.falldetector.util.simulateClick
import com.example.bpawlowski.falldetector.util.snackbar
import kotlinx.android.synthetic.main.fragment_camera.buttonRotate
import kotlinx.android.synthetic.main.fragment_camera.buttonShutter
import kotlinx.android.synthetic.main.fragment_camera.viewFinder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.io.File

const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"

class CameraFragment : BaseFragment<CameraViewState>() { // todo review this fragment

    override val layoutResID = R.layout.fragment_camera

    override val viewModel: CameraViewModel by sharedViewModel()

    override val shouldShowNavigation = false

    override val shouldShowActionBar = false

    private var lensFacing = CameraX.LensFacing.BACK

    private val cameraControl: CameraControl
        get() = CameraX.getCameraControl(lensFacing)

    private lateinit var broadcastManager: LocalBroadcastManager

    override fun invalidate(state: CameraViewState) {}

    private val volumeDownReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val keyCode = intent.getIntExtra(KEY_EVENT_EXTRA, KeyEvent.KEYCODE_UNKNOWN)
            if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                buttonShutter.simulateClick()
            }
        }
    }

    private val photoCapturedListener by lazy {
        object : ImageCapture.OnImageSavedListener {

            override fun onError(
                imageCaptureError: ImageCapture.ImageCaptureError,
                message: String,
                cause: Throwable?
            ) {
                snackbar(getString(R.string.toast_photo_failed))
                Timber.e(imageCaptureError.name, "Photo capture failed: $message")
            }

            override fun onImageSaved(file: File) {
                handlePhotoCaptured(file)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        broadcastManager = LocalBroadcastManager.getInstance(requireContext())

        val filter = IntentFilter().apply { addAction(KEY_EVENT_ACTION) }
        broadcastManager.registerReceiver(volumeDownReceiver, filter)

        viewFinder.post { bindCameraUseCases() } // todo orientation change
        buttonRotate.setOnClickListener {
            rotateCamera()
        }
        setUpTapToFocus()
    }

    override fun onResume() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onResume()
    }

    override fun onPause() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        broadcastManager.unregisterReceiver(volumeDownReceiver)
    }

    private fun bindCameraUseCases() {
        CameraX.unbindAll()

        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)

        val previewConfig = PreviewConfig.Builder().apply {
            setLensFacing(lensFacing)
            setTargetAspectRatio(screenAspectRatio)
            setTargetRotation(viewFinder.display.rotation)
        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)
            viewFinder.surfaceTexture = it.surfaceTexture
        }

        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setLensFacing(lensFacing)
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
        }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)
        buttonShutter.setOnClickListener {
            val file = File(
                requireContext().externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg"
            )
            imageCapture.takePicture(file, photoCapturedListener)
        }

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    @SuppressLint("RestrictedApi")
    private fun rotateCamera() {
        lensFacing = if (lensFacing == CameraX.LensFacing.FRONT) {
            buttonShutter.setImageResource(R.drawable.ic_camera_front_black_24dp)
            CameraX.LensFacing.BACK
        } else {
            buttonRotate.setImageResource(R.drawable.ic_camera_rear_black_24dp)
            CameraX.LensFacing.FRONT
        }
        try {
            CameraX.getCameraWithLensFacing(lensFacing)
            bindCameraUseCases()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpTapToFocus() {
        viewFinder.setOnTouchListener { _, event ->

            if (event.action != MotionEvent.ACTION_UP) {
                return@setOnTouchListener false
            }

            val factory = TextureViewMeteringPointFactory(viewFinder)
            val point = factory.createPoint(event.x, event.y)
            val action = FocusMeteringAction.Builder.from(point).build()
            cameraControl.startFocusAndMetering(action)
            return@setOnTouchListener true
        }
    }

    private fun handlePhotoCaptured(file: File) { // fixme - glitch with animation - bright square
        viewModel.onPhotoCaptured(file)
        postDelayed {
            findNavController().popBackStack()
        }
    }
}
