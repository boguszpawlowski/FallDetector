package com.example.bpawlowski.falldetector.ui.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bpawlowski.database.util.postDelayed
import com.bpawlowski.system.model.AppSettings
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding
import com.example.bpawlowski.falldetector.util.getPermissions
import com.example.bpawlowski.falldetector.util.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CHANGE_THEME_DELAY = 450L

class MainActivity : BaseActivity<ActivityMainBinding>() {

	override val layoutId = R.layout.activity_main

	override val viewModel: MainViewModel by viewModel()

	private var currentNavController: LiveData<NavController>? = null

	private val appSettingsObserver: Observer<AppSettings> by lazy {
		Observer<AppSettings> { appSettings ->
			appSettings?.let { updateApp(it) }
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setSupportActionBar(binding.toolbar)

		if (savedInstanceState == null) {
			setupBottomNavigation()
		}
		viewModel.initiateServiceState()
		viewModel.appSettingsPreferencesData.observe(this, appSettingsObserver)
	}

	override fun onStart() {
		super.onStart()

		checkPermissions()
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
		super.onRestoreInstanceState(savedInstanceState)

		setupBottomNavigation()
	}

	override fun onSupportNavigateUp() = currentNavController?.value?.navigateUp() ?: false

	private fun setupBottomNavigation() {
		val bottomNavigation = binding.bottomNavigation

		val navGraphIds = listOf(R.navigation.home, R.navigation.contacts, R.navigation.alarm, R.navigation.settings)

		val controller = bottomNavigation.setupWithNavController(
			navGraphIds = navGraphIds,
			fragmentManager = supportFragmentManager,
			containerId = R.id.nav_host_container,
			intent = intent
		)

		controller.observe(this, Observer { navController ->
			setupActionBarWithNavController(navController)
		})

		currentNavController = controller
	}

	private fun updateApp(appSettings: AppSettings) {
		postDelayed(CHANGE_THEME_DELAY) {
			AppCompatDelegate.setDefaultNightMode(if (appSettings.darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
		}
		viewModel.changeServiceState(appSettings)
	}

	private fun checkPermissions() =
		getPermissions(
			this,
			listOf(
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.CALL_PHONE,
				Manifest.permission.SEND_SMS,
				Manifest.permission.READ_PHONE_STATE
			)
		)
}
