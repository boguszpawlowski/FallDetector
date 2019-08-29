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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureConfig
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.bpawlowski.database.util.postDelayed
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.fragment.BaseFragment
import com.example.bpawlowski.falldetector.databinding.FragmentCameraBinding
import com.example.bpawlowski.falldetector.screens.main.MainActivity
import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.util.setVisible
import com.example.bpawlowski.falldetector.util.simulateClick
import com.example.bpawlowski.falldetector.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_camera.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File

const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"

class CameraFragment : BaseFragment<FragmentCameraBinding>() {  //todo review this fragment

	override val layoutResID = R.layout.fragment_camera

	override val sharedViewModel: MainViewModel by sharedViewModel()

	override val viewModel: CameraViewModel by viewModel()

	private var lensFacing = CameraX.LensFacing.BACK

	private lateinit var broadcastManager: LocalBroadcastManager

	private val volumeDownReceiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context, intent: Intent) {
			val keyCode = intent.getIntExtra(KEY_EVENT_EXTRA, KeyEvent.KEYCODE_UNKNOWN)
			if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
				binding.btnShutter.simulateClick()
			}
		}
	}

	private val photoCapturedListener by lazy {
		object : ImageCapture.OnImageSavedListener {
			override fun onError(
				error: ImageCapture.UseCaseError,
				message: String,
				exc: Throwable?
			) {
				toast(getString(R.string.toast_photo_failed))
				Timber.e(exc, "Photo capture failed: $message")
			}

			override fun onImageSaved(file: File) {
				toast(getString(R.string.toast_photo_success))
				Timber.d(file.absolutePath)
				handlePhotoCaptured(file)
			}
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		(activity as? MainActivity)?.let {
			it.supportActionBar?.hide()
			it.bottom_navigation.setVisible(false)
		}

		return super.onCreateView(inflater, container, savedInstanceState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		broadcastManager = LocalBroadcastManager.getInstance(requireContext())

		val filter = IntentFilter().apply { addAction(KEY_EVENT_ACTION) }
		broadcastManager.registerReceiver(volumeDownReceiver, filter)

		binding.viewFinder.post { bindCameraUseCases() } //todo orientation change
		binding.btnRotate.setOnClickListener {
			rotateCamera()
		}
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
		(activity as? MainActivity)?.let {
			it.supportActionBar?.show()
			it.bottom_navigation.setVisible(true)
		}
		broadcastManager.unregisterReceiver(volumeDownReceiver)
	}

	private fun bindCameraUseCases() {
		CameraX.unbindAll()

		val metrics = DisplayMetrics().also { view_finder.display.getRealMetrics(it) }
		val screenAspectRatio = Rational(metrics.widthPixels, metrics.heightPixels)

		val previewConfig = PreviewConfig.Builder().apply {
			setLensFacing(lensFacing)
			setTargetAspectRatio(screenAspectRatio)
			setTargetRotation(view_finder.display.rotation)
		}.build()

		val preview = Preview(previewConfig)

		preview.setOnPreviewOutputUpdateListener {
			val parent = view_finder.parent as ViewGroup
			parent.removeView(view_finder)
			parent.addView(view_finder, 0)
			view_finder.surfaceTexture = it.surfaceTexture
		}

		val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
			setLensFacing(lensFacing)
			setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
		}.build()

		val imageCapture = ImageCapture(imageCaptureConfig)
		binding.btnShutter.setOnClickListener {
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
			binding.btnRotate.setImageResource(R.drawable.ic_camera_front_black_24dp)
			CameraX.LensFacing.BACK
		} else {
			binding.btnRotate.setImageResource(R.drawable.ic_camera_rear_black_24dp)
			CameraX.LensFacing.FRONT
		}
		try {
			CameraX.getCameraWithLensFacing(lensFacing)
			bindCameraUseCases()
		} catch (e: Exception) {
			Timber.e(e)
		}
	}

	private fun handlePhotoCaptured(file: File) { //fixme - glitch with animation - bright square
		sharedViewModel.onPhotoAdded(file)
		postDelayed {
			findNavController().navigateUp()
		}
	}
}