package com.example.bpawlowski.falldetector.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import bogusz.com.service.model.AppSettings
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding
import com.example.bpawlowski.falldetector.ui.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.ui.main.home.HomeFragmentDirections
import com.example.bpawlowski.falldetector.util.getPermissions
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var navController: NavController

    private val appSettingsObserver: Observer<AppSettings> by lazy {
        Observer<AppSettings> { appSettings ->
            appSettings?.let { updateApp(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        setupActionBarWithNavController(navController, binding.drawerLayout)
        setupWithNavController(binding.navView, navController)

        binding.navView.setNavigationItemSelectedListener(this)

        initObservers()
        checkPermissions()
    }

    override fun onBackPressed() {
        binding.navView.checkedItem?.isChecked = false

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> {
                navController.navigate(
                    HomeFragmentDirections.showSettings()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> navController.navigate(R.id.homeFragment)
            R.id.nav_contacts -> navController.navigate(R.id.contactsFragment)
            R.id.nav_alarm -> navController.navigate(R.id.alarmFragment)
            R.id.nav_settings -> navController.navigate(R.id.settingsFragment)

            R.id.nav_call -> navController.navigate(R.id.callFragment)
            R.id.nav_sms -> navController.navigate(R.id.messageFragment)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            item.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }, CLOSE_DRAWER_DELAY)

        return true
    }

    override fun onSupportNavigateUp() = navigateUp(findNavController(R.id.nav_host_fragment), binding.drawerLayout)

    private fun initObservers() {
        viewModel.appSettingsPreferencesData.observe(this, appSettingsObserver)
    }

    private fun updateApp(appSettings: AppSettings) {
        //TODO change DARK mode, sensitivity etc.
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

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getLayoutID() = R.layout.activity_main

    override val keepInBackStack = true

    companion object {
        private const val CLOSE_DRAWER_DELAY = 200L

        @JvmStatic
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
