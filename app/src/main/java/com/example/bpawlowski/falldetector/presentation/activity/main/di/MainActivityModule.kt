package com.example.bpawlowski.falldetector.presentation.activity.main.di

import com.example.bpawlowski.falldetector.presentation.activity.main.contacts.ContactsFragment
import com.example.bpawlowski.falldetector.presentation.activity.main.home.HomeFragment
import com.example.bpawlowski.falldetector.presentation.activity.main.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule{

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindContactsFragment(): ContactsFragment

    @MainActivityScope
    @ContributesAndroidInjector
    abstract fun bindSettingsFragment(): SettingsFragment
}