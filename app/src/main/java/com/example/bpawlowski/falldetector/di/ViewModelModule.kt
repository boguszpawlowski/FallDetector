package com.example.bpawlowski.falldetector.di

import com.example.bpawlowski.falldetector.ui.main.MainViewModel
import com.example.bpawlowski.falldetector.ui.main.alarm.AlarmViewModel
import com.example.bpawlowski.falldetector.ui.main.call.CallViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.ContactsViewModel
import com.example.bpawlowski.falldetector.ui.main.contacts.FormDialogViewModel
import com.example.bpawlowski.falldetector.ui.main.home.HomeViewModel
import com.example.bpawlowski.falldetector.ui.main.sms.MessageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
	viewModel { MessageViewModel(get(), get(), get()) }
	viewModel { HomeViewModel(get()) }
	viewModel { ContactsViewModel(get()) }
	viewModel { FormDialogViewModel(get()) }
	viewModel { CallViewModel(get(), get()) }
	viewModel { MainViewModel(get(), get()) }
	viewModel { AlarmViewModel(get(), get(), get()) }
}