package com.example.bpawlowski.falldetector.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import bogusz.com.service.model.AppSettings
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding
import com.example.bpawlowski.falldetector.ui.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.ui.main.alarm.AlarmFragment
import com.example.bpawlowski.falldetector.ui.main.call.CallFragment
import com.example.bpawlowski.falldetector.ui.main.contacts.ContactsFragment
import com.example.bpawlowski.falldetector.ui.main.home.HomeFragment
import com.example.bpawlowski.falldetector.ui.main.settings.SettingsFragment
import com.example.bpawlowski.falldetector.ui.main.sms.MessageFragment
import com.example.bpawlowski.falldetector.util.getPermissions
import com.example.bpawlowski.falldetector.util.instantiate
import com.example.bpawlowski.falldetector.util.screenTitles
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.app_bar_navigation.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {

    private val appSettingsObserver: Observer<AppSettings> by lazy {
        Observer<AppSettings> { appSettings ->
            appSettings?.let { updateApp(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            changeView(HomeFragment::class.java, false)
        }

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
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
                changeView(SettingsFragment::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> changeView(HomeFragment::class.java)
            R.id.nav_contacts -> changeView(ContactsFragment::class.java)
            R.id.nav_alarm -> changeView(AlarmFragment::class.java)
            R.id.nav_settings -> changeView(SettingsFragment::class.java)

            R.id.nav_call -> changeView(CallFragment::class.java)
            R.id.nav_sms -> changeView(MessageFragment::class.java)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            item.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }, CLOSE_DRAWER_DELAY)

        return true
    }

    override fun onNavigateUp(): Boolean {
        binding.navView.menu.getItem(0).isChecked = true

        return super.onNavigateUp()
    }

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

    private fun changeView(fragmentClass: Class<*>, keepInBackStack: Boolean = true) {
        val newFragment =
            supportFragmentManager.findFragmentByTag(fragmentClass.canonicalName) ?: supportFragmentManager.instantiate(
                fragmentClass
            )

        supportFragmentManager.popBackStack(FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, newFragment, fragmentClass.canonicalName)
            if (keepInBackStack) addToBackStack(FRAGMENT_TAG)
            commit()
        }
        supportFragmentManager.executePendingTransactions()

        toolbar.title = screenTitles[fragmentClass]
    }

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getLayoutID() = R.layout.activity_main

    override val keepInBackStack = true

    companion object {
        private const val CLOSE_DRAWER_DELAY = 200L
        private const val FRAGMENT_TAG = "fragment_tag"

        @JvmStatic
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
