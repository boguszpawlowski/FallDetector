package com.example.bpawlowski.falldetector.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.presentation.activity.main.MainViewModel
import com.example.bpawlowski.falldetector.presentation.activity.main.call.CallViewModel
import com.example.bpawlowski.falldetector.presentation.activity.main.contacts.ContactsViewModel
import com.example.bpawlowski.falldetector.presentation.activity.main.contacts.FormDialogViewModel
import com.example.bpawlowski.falldetector.presentation.activity.main.home.HomeViewModel
import com.example.bpawlowski.falldetector.presentation.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindContactsViewModel(contactsViewModel: ContactsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FormDialogViewModel::class)
    abstract fun bindFormDialogViewModel(formDialogViewModel: FormDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CallViewModel::class)
    abstract fun bindCallViewModel(callViewModel: CallViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}