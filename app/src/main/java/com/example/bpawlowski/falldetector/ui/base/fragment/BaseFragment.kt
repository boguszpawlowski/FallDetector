package com.example.bpawlowski.falldetector.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bpawlowski.falldetector.BR
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.ui.base.activity.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, SVM : BaseViewModel, B : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var parentViewModel: SVM

    lateinit var binding: B

    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.v("ON_CREATE")

        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        parentViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(getParentViewModeClass())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.v("ON_CREATE_VIEW")

        binding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        return binding.root
    }

    override fun onStart() {
        Timber.v("ON_START")
        super.onStart()
    }

    override fun onResume() {
        Timber.v("ON_RESUME")
        super.onResume()

        viewModel.onResume()
    }

    override fun onPause() {
        Timber.v("ON_PAUSE")
        super.onPause()
    }

    override fun onStop() {
        Timber.v("ON_STOP")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.v("ON_DESTROY")
        super.onDestroy()

        disposable.dispose()
    }

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getLayoutID(): Int

    abstract fun getParentViewModeClass(): Class<SVM>
}