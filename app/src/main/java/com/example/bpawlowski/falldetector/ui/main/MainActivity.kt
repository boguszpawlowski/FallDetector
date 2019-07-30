package com.example.bpawlowski.falldetector.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import bogusz.com.service.model.AppSettings
import bogusz.com.service.util.postDelayed
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding
import com.example.bpawlowski.falldetector.ui.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.util.drawerItems
import com.example.bpawlowski.falldetector.util.getPermissions
import com.example.bpawlowski.falldetector.util.showPopupMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
					 BottomNavigationView.OnNavigationItemSelectedListener {

	override val viewModel: MainViewModel by viewModel()

	private var navController: NavController? = null

	private val navStack = ArrayDeque<Int>()

	private val appBarConfiguration by lazy {
		AppBarConfiguration(drawerItems)
	}

	private val appSettingsObserver: Observer<AppSettings> by lazy {
		Observer<AppSettings> { appSettings ->
			appSettings?.let { updateApp(it) }
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setSupportActionBar(toolbar)

		navController = findNavController(R.id.nav_host_fragment).also {
			setupActionBarWithNavController(it, appBarConfiguration)
			binding.bottomNavigation.setupWithNavController(it)
		}

		binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

		initObservers()
		checkPermissions()
	}

	override fun onBackPressed() {
		val selectedItem = binding.bottomNavigation.selectedItemId
		if (selectedItem != R.id.home) {
			navController?.navigateUp(appBarConfiguration)
		} else {
			finish()
		}
	}

	override fun onDestroy() {
		super.onDestroy()

		navController = null
	}

	override fun onOptionsItemSelected(item: MenuItem) =
		when (item.itemId) {
			R.id.action_settings -> {
				navController?.navigate(R.id.settingsFragment)
				true
			}

			else -> super.onOptionsItemSelected(item)
		}

	override fun onNavigationItemSelected(item: MenuItem): Boolean =
		when (item.itemId) {
			R.id.nav_home -> {
				navController?.navigate(R.id.homeFragment)
				navStack.pushIfAbsent(R.id.homeFragment)
				true
			}

			R.id.nav_contacts -> {
				navController?.navigate(R.id.contactsFragment)
				navStack.pushIfAbsent(R.id.contactsFragment)
				true
			}

			R.id.nav_alarm -> {
				navController?.navigate(R.id.alarmFragment)
				navStack.pushIfAbsent(R.id.contactsFragment)
				true
			}

			R.id.nav_more -> {
				showPopupMenu(findViewById(R.id.nav_more), R.menu.navigation, getOnPopupMenuItemClickListener())
				false
			}

			else -> false
		}

	override fun onSupportNavigateUp() =
		navigateUp(navController!!, appBarConfiguration)

	private fun initObservers() = with(viewModel) {
		appSettingsPreferencesData.observe(this@MainActivity, appSettingsObserver)
	}

	private fun Deque<Int>.pushIfAbsent(value: Int){
		removeFirstOccurrence(value)
		push(value)
	}

	private fun clearSelection() {
		val menu = binding.bottomNavigation.menu
		for (i in 0 until menu.size()) {
			menu.getItem(i).isCheckable = false
		}
		for (i in 0 until menu.size()) {
			menu.getItem(i).isCheckable = true
		}
	}

	private fun updateApp(appSettings: AppSettings) {
		//TODO change DARK mode, sensitivity etc.
		val (darkMode, sendingSms, sensingLocation, sensitivity) = appSettings
		postDelayed(CHANGE_THEME_DELAY) {
			AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
		}
		viewModel.changeSensitivity(sensitivity)
	}

	private fun getOnPopupMenuItemClickListener() = PopupMenu.OnMenuItemClickListener { item ->
		when (item.itemId) {
			R.id.action_settings -> {
				navController?.navigate(R.id.settingsFragment)
				clearSelection()
				true
			}

			else -> super.onOptionsItemSelected(item)
		}
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

	override val layoutId = R.layout.activity_main

	override val keepInBackStack = true

	companion object {
		private const val CHANGE_THEME_DELAY = 450L

		@JvmStatic
		fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
	}
}
