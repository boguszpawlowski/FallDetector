package com.example.bpawlowski.falldetector.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.MviState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseDialogFragment<S : MviState> : DialogFragment() {

    private lateinit var viewLifecycleJob: Job

    protected val viewScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + viewLifecycleJob)

    abstract val layoutResID: Int

    abstract val viewModel: BaseViewModel<S>

    abstract fun invalidate(state: S)

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).v("ON_CREATE")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(javaClass.simpleName).v("ON_CREATE_VIEW")
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutResID, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).v("ON_VIEW_CREATED")
        viewLifecycleJob = Job()
        super.onViewCreated(view, savedInstanceState)

        viewScope.launch {
            viewModel.stateFlow.collect {
                invalidate(it)
            }
        }
    }

    override fun onStart() {
        Timber.tag(javaClass.simpleName).v("ON_START")
        super.onStart()
    }

    override fun onResume() {
        Timber.tag(javaClass.simpleName).v("ON_RESUME")
        super.onResume()
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
        Timber.tag(javaClass.simpleName).v("ON_DESTROY_VIEW")
        if (::viewLifecycleJob.isInitialized) {
            viewLifecycleJob.cancel()
        }
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.tag(javaClass.simpleName).v("ON_DESTROY")
        super.onDestroy()
    }

    protected fun clearRootFocus() {
        view?.rootView?.findFocus()?.let { view ->
            view.clearFocus()
        }
    }
}
