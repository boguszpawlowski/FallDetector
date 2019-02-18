package com.example.bpawlowski.falldetector.presentation.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.ViewModelFactory
import com.example.bpawlowski.falldetector.presentation.activity.main.MainViewModel
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
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}