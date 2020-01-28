package com.example.bpawlowski.falldetector.di

import android.content.Context
import com.example.bpawlowski.falldetector.domain.AppPreferenceDelegate
import com.example.bpawlowski.falldetector.screens.MainViewModel
import com.example.bpawlowski.falldetector.screens.alarm.AlarmViewModel
import com.example.bpawlowski.falldetector.screens.camera.CameraViewModel
import com.example.bpawlowski.falldetector.screens.contacts.list.ContactsViewModel
import com.example.bpawlowski.falldetector.screens.contacts.add.AddContactViewModel
import com.example.bpawlowski.falldetector.screens.contacts.details.ContactDetailsViewModel
import com.example.bpawlowski.falldetector.screens.home.HomeViewModel
import com.example.bpawlowski.falldetector.screens.preference.PreferenceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    single {
        AppPreferenceDelegate(
            androidContext().getSharedPreferences(
                "Preference",
                Context.MODE_PRIVATE
            )
        )
    }

    viewModel { HomeViewModel(serviceStateRepository = get()) }
    viewModel {
        ContactsViewModel(
            contactsRepository = get(),
            connectivityService = get(),
            locationProvider = get()
        )
    }
    viewModel { AddContactViewModel(contactsRepository = get()) }
    viewModel {
        MainViewModel(
            preferenceDelegate = get(),
            serviceStateRepository = get()
        )
    }
    viewModel {
        AlarmViewModel(
            locationProvider = get(),
            contactRepository = get(),
            alarmService = get()
        )
    }
    viewModel {
        ContactDetailsViewModel(
            contactsRepository = get(),
            connectivityService = get(),
            locationProvider = get()
        )
    }
    viewModel { CameraViewModel() }
    viewModel { PreferenceViewModel(appPreferenceDelegate = get()) }
}
