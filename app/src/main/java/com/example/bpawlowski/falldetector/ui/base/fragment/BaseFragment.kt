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
import com.example.bpawlowski.falldetector.di.Injectable
import com.example.bpawlowski.falldetector.ui.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.ui.base.activity.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, SVM : BaseViewModel, B : ViewDataBinding> : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    lateinit var parentViewModel: SVM

    lateinit var binding: B

    val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).v("ON_CREATE")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag(javaClass.simpleName).v("ON_CREATE_VIEW")

        binding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).v("ON_VIEW_CREATED")
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        parentViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(getParentViewModeClass())
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun onStart() {
        Timber.tag(javaClass.simpleName).v("ON_START")
        super.onStart()
    }

    override fun onResume() {
        Timber.tag(javaClass.simpleName).v("ON_RESUME")
        super.onResume()

        viewModel.onResume()
    }

    override fun onPause() {
        Timber.tag(javaClass.simpleName).v("ON_PAUSE")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag(javaClass.simpleName).v("ON_STOP")
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        disposable.dispose()
    }

    override fun onDestroy() {
        Timber.tag(javaClass.simpleName).v("ON_DESTROY")
        super.onDestroy()
    }

    abstract fun getViewModelClass(): Class<VM>

    abstract fun getLayoutID(): Int

    abstract fun getParentViewModeClass(): Class<SVM>
}