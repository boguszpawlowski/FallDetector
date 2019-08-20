package com.example.bpawlowski.falldetector.di

import com.example.bpawlowski.falldetector.screens.main.MainViewModel
import com.example.bpawlowski.falldetector.screens.main.alarm.AlarmViewModel
import com.example.bpawlowski.falldetector.screens.main.contacts.ContactsViewModel
import com.example.bpawlowski.falldetector.screens.main.contacts.FormDialogViewModel
import com.example.bpawlowski.falldetector.screens.main.details.ContactDetailsViewModel
import com.example.bpawlowski.falldetector.screens.main.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
	viewModel { HomeViewModel(get()) }
	viewModel { ContactsViewModel(get(), get(), get(), get()) }
	viewModel { FormDialogViewModel(get()) }
	viewModel { MainViewModel(get(named("Default")), get()) }
	viewModel { AlarmViewModel(get(), get(), get()) }
	viewModel { ContactDetailsViewModel(get(), get(), get(), get()) }
}