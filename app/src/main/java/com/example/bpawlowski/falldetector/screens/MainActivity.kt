package com.example.bpawlowski.falldetector.screens

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding
import com.example.bpawlowski.falldetector.screens.camera.KEY_EVENT_ACTION
import com.example.bpawlowski.falldetector.screens.camera.KEY_EVENT_EXTRA
import com.example.bpawlowski.falldetector.screens.preference.model.AppPreferences
import com.example.bpawlowski.falldetector.util.BottomNavigationManager
import com.example.bpawlowski.falldetector.util.SnackbarManager
import com.example.bpawlowski.falldetector.util.SnackbarPayload
import com.example.bpawlowski.falldetector.util.getPermissions
import com.example.bpawlowski.falldetector.util.postDelayed
import com.example.bpawlowski.falldetector.util.setVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.bpawlowski.falldetector.util.setupWithNavController

private const val CHANGE_THEME_DELAY = 250L

class MainActivity : BaseActivity<MainViewState>() {

    override val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private lateinit var currentNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
        viewModel.initiateServiceState()

        viewModel.appSettingsFlow.onEach {
            updateAppUi(it)
        }.launchIn(viewScope)

        SnackbarManager.messageFlow.onEach {
            showSnackbar(it)
        }.launchIn(viewScope)

        BottomNavigationManager.flow.onEach { configuration ->
            binding.bottomNavigation.setVisible(configuration.showBottomNaigation)
            if (configuration.showActionBar) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }.launchIn(viewScope)
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    } // TODO adding photo not working

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigation()
    }

    override fun onSupportNavigateUp() = currentNavController.navigateUp()

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                val intent =
                    Intent(KEY_EVENT_ACTION).apply { putExtra(KEY_EVENT_EXTRA, keyCode) }
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                true
            }

            else -> super.onKeyDown(keyCode, event)
        }
    }

    private fun setupBottomNavigation() {
        val navGraphIds = listOf(
            R.navigation.home,
            R.navigation.contacts,
            R.navigation.alarm,
            R.navigation.settings
        )

        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.navHostContainer,
            intent = intent
        )

        controller.onEach { navController ->
            currentNavController = navController
            setupActionBarWithNavController(navController)
        }.launchIn(lifecycleScope)
    }

    private fun updateAppUi(appPreferences: AppPreferences) {
        postDelayed(CHANGE_THEME_DELAY) {
            AppCompatDelegate.setDefaultNightMode(if (appPreferences.darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
        viewModel.changeServiceState(appPreferences)
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

    private fun showSnackbar(payload: SnackbarPayload) {
        if (payload.actionResId == null) {
            Snackbar.make(binding.snackbarContainer, payload.message, Snackbar.LENGTH_LONG).show()
        } else {
            with(Snackbar.make(binding.snackbarContainer, payload.message, Snackbar.LENGTH_LONG)) {
                setAction(payload.actionResId, payload.actionListener)
                show()
            }
        }
    }
}
