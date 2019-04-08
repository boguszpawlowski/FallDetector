package com.example.bpawlowski.falldetector.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bpawlowski.falldetector.activity.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.activity.main.MainViewModel
import com.example.bpawlowski.falldetector.activity.main.call.CallViewModel
import com.example.bpawlowski.falldetector.activity.main.contacts.ContactsViewModel
import com.example.bpawlowski.falldetector.activity.main.contacts.FormDialogViewModel
import com.example.bpawlowski.falldetector.activity.main.home.HomeViewModel
import com.example.bpawlowski.falldetector.activity.main.sms.MessageViewModel
import com.example.bpawlowski.falldetector.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindMessageViewModel(messageViewModel: MessageViewModel): ViewModel

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
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}