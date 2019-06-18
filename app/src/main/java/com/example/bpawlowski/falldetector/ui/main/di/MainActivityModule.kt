package com.example.bpawlowski.falldetector.ui.main.di

import com.example.bpawlowski.falldetector.ui.main.alarm.AlarmFragment
import com.example.bpawlowski.falldetector.ui.main.call.CallFragment
import com.example.bpawlowski.falldetector.ui.main.contacts.ContactsFragment
import com.example.bpawlowski.falldetector.ui.main.contacts.FormDialogFragment
import com.example.bpawlowski.falldetector.ui.main.home.HomeFragment
import com.example.bpawlowski.falldetector.ui.main.settings.SettingsFragment
import com.example.bpawlowski.falldetector.ui.main.sms.MessageFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindMessageFragment(): MessageFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindContactsFragment(): ContactsFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindSettingsFragment(): SettingsFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindFormDialogFragment(): FormDialogFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindCallFragment(): CallFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindAlarmFragment(): AlarmFragment
}