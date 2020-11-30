package com.example.bpawlowski.falldetector.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.bpawlowski.falldetector.domain.MviState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity<S : MviState> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<S>

    protected val viewScope: LifecycleCoroutineScope
        get() = lifecycleScope

    open fun invalidate(state: S) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.stateFlow.onEach { invalidate(it) }.launchIn(viewScope)
    }
}
