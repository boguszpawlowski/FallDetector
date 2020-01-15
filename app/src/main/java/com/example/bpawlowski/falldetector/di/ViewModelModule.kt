package com.example.bpawlowski.falldetector.di

import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.screens.main.alarm.AlarmViewModel
import com.example.bpawlowski.falldetector.screens.main.camera.CameraViewModel
import com.example.bpawlowski.falldetector.screens.main.contacts.ContactsViewModel
import com.example.bpawlowski.falldetector.screens.main.contacts.AddContactViewModel
import com.example.bpawlowski.falldetector.screens.main.details.ContactDetailsViewModel
import com.example.bpawlowski.falldetector.screens.main.events.EventDetailsViewModel
import com.example.bpawlowski.falldetector.screens.main.home.HomeViewModel
import com.example.bpawlowski.falldetector.screens.main.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel {
        ContactsViewModel(
            contactsRepository = get(),
            connectivityService = get(),
            locationProvider = get()
        )
    }
    viewModel { AddContactViewModel(get()) }
    viewModel { MainViewModel(get(named("Default")), get()) }
    viewModel { AlarmViewModel(get(), get(), get()) }
    viewModel { ContactDetailsViewModel(get(), get(), get()) }
    viewModel { CameraViewModel() }
    viewModel {
        MapViewModel(
            eventRepository = get(),
            locationProvider = get()
        )
    }
    viewModel { EventDetailsViewModel(get()) }
}
