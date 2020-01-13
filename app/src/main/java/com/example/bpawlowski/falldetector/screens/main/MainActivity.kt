package com.example.bpawlowski.falldetector.screens.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bpawlowski.system.model.AppSettings
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.screens.main.camera.KEY_EVENT_ACTION
import com.example.bpawlowski.falldetector.screens.main.camera.KEY_EVENT_EXTRA
import com.example.bpawlowski.falldetector.util.BottomNavigationManager
import com.example.bpawlowski.falldetector.util.SnackbarManager
import com.example.bpawlowski.falldetector.util.SnackbarPayload
import com.example.bpawlowski.falldetector.util.getPermissions
import com.example.bpawlowski.falldetector.util.postDelayed
import com.example.bpawlowski.falldetector.util.setVisible
import com.example.bpawlowski.falldetector.util.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import kotlinx.android.synthetic.main.activity_main.snackbarContainer
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CHANGE_THEME_DELAY = 450L

class MainActivity : BaseActivity<MainViewState>() { // todo hide keyboard on click outside

    override val layoutId = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    private var currentNavController: LiveData<NavController>? = null

    private val appSettingsObserver: Observer<AppSettings> by lazy {
        Observer<AppSettings> { appSettings ->
            appSettings?.let { updateAppUi(it) }
        }
    }

    override fun invalidate(state: MainViewState) {}

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
        viewModel.initiateServiceState()
        viewModel.appSettingsPreferencesData.observe(this, appSettingsObserver)

        viewScope.launch {
            SnackbarManager.messageChannel.consumeEach { showSnackbar(it) }
        }

        viewScope.launch {
            BottomNavigationManager.broadcastChannel.consumeEach { configuration ->
                bottomNavigation.setVisible(configuration.showBottomNaigation)
                if (configuration.showActionBar) {
                    supportActionBar?.show()
                } else {
                    supportActionBar?.hide()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        checkPermissions()
    } //TODO adding photo not working

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigation()
    }

    override fun onSupportNavigateUp() = currentNavController?.value?.navigateUp() ?: false

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
        val bottomNavigation = bottomNavigation

        val navGraphIds = listOf(
            R.navigation.home,
            R.navigation.contacts,
            R.navigation.alarm,
            R.navigation.map,
            R.navigation.settings
        )

        val controller = bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.navHostContainer,
            intent = intent
        )

        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })

        currentNavController = controller
    }

    private fun updateAppUi(appSettings: AppSettings) {
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

    private fun showSnackbar(payload: SnackbarPayload) {
        if (payload.actionResId == null) {
            Snackbar.make(snackbarContainer, payload.message, Snackbar.LENGTH_LONG).show()
        } else {
            with(Snackbar.make(snackbarContainer, payload.message, Snackbar.LENGTH_LONG)) {
                setAction(payload.actionResId, payload.actionListener)
                show()
            }
        }
    }
}
