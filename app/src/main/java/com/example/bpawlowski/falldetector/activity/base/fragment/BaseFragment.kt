package com.example.bpawlowski.falldetector.activity.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.activity.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.activity.base.activity.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, SVM : BaseViewModel, B : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var parentViewModel: SVM

    lateinit var binding: B

    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        parentViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(getParentViewModeClass())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.dispose()
    }

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getLayoutID(): Int

    abstract fun getParentViewModeClass(): Class<SVM>
}