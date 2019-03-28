package com.example.bpawlowski.falldetector.presentation.activity.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.databinding.ActivityMainBinding
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.BaseActivity
import com.example.bpawlowski.falldetector.presentation.activity.main.call.CallFragment
import com.example.bpawlowski.falldetector.presentation.activity.main.contacts.ContactsFragment
import com.example.bpawlowski.falldetector.presentation.activity.main.home.HomeFragment
import com.example.bpawlowski.falldetector.presentation.util.doNothing
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_navigation.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        fragmentManager = supportFragmentManager
        if (savedInstanceState == null) {
            changeView(HomeFragment::class.java)
        }
        viewModel.stateSubject.subscribe {
            when (it) {
                is MainScreenState.ErrorState -> Snackbar.make(
                    this.nav_view,
                    it.error.message.orEmpty(),
                    Snackbar.LENGTH_SHORT
                ).show()
                else -> {
                }
            }
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> changeView(HomeFragment::class.java)
            R.id.nav_contacts -> changeView(ContactsFragment::class.java)
            R.id.nav_alarm -> doNothing
            R.id.nav_settings -> doNothing

            R.id.nav_call -> changeView(CallFragment::class.java)
            R.id.nav_sms -> doNothing
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeView(fragmentClass: Class<*>) {

        val newFragment =
            fragmentManager.findFragmentByTag(fragmentClass.canonicalName) ?: instantiateFragment(fragmentClass)

        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, newFragment)

        transaction.commit()
    }

    private fun instantiateFragment(fragmentClass: Class<*>): Fragment {
        return Fragment.instantiate(this, fragmentClass.canonicalName)
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun getLayoutID(): Int = R.layout.activity_main

    override fun keepInBackStack(): Boolean = true

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }
}
