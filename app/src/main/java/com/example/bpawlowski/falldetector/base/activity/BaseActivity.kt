package com.example.bpawlowski.falldetector.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bpawlowski.falldetector.domain.MviState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseActivity<S : MviState> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<S>

    abstract val layoutId: Int

    lateinit var job: Job

    protected val viewScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + job)

    abstract fun invalidate(state: S)

    override fun onCreate(savedInstanceState: Bundle?) {
        job = Job()
        Timber.tag(javaClass.simpleName).v("ON_CREATE")
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
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

    override fun onDestroy() {
        Timber.tag(javaClass.simpleName).v("ON_DESTROY")
        if (::job.isInitialized) job.cancel()
        super.onDestroy()
    }
}
