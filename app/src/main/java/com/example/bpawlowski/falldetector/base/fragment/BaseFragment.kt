@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.bpawlowski.falldetector.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.example.bpawlowski.falldetector.base.activity.BaseViewModel
import com.example.bpawlowski.falldetector.domain.MviState
import com.example.bpawlowski.falldetector.util.toggleNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

abstract class BaseFragment<S : MviState> : Fragment() {

    protected val viewScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope

    protected open val shouldShowNavigation = true

    protected open val shouldShowActionBar = true

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
        toggleNavigation(shouldShowNavigation, shouldShowActionBar)

        return inflater.inflate(layoutResID, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).v("ON_VIEW_CREATED")
        super.onViewCreated(view, savedInstanceState)

        viewModel.stateFlow.onEach {
            invalidate(it)
        }.launchIn(viewScope)
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

    protected fun hideKeyBoard() = requireActivity().currentFocus?.let {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
