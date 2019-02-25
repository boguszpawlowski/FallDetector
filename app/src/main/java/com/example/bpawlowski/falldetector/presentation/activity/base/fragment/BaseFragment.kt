package com.example.bpawlowski.falldetector.presentation.activity.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.bpawlowski.falldetector.presentation.activity.base.activity.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, SVM: ViewModel, B : ViewDataBinding> : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var parentViewModel: SVM

    lateinit var binding: B

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        parentViewModel = ViewModelProviders.of(getParentActivity(), viewModelFactory).get(getParentViewModeClass())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getLayoutID(): Int

    abstract fun getParentActivity(): FragmentActivity

    abstract fun getParentViewModeClass(): Class<SVM>
}