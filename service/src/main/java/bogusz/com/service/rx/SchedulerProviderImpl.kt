package bogusz.com.service.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


internal class SchedulerProviderImpl @Inject constructor() : SchedulerProvider {
    override val MAIN: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val IO: Scheduler
        get() = Schedulers.io()
    override val COMPUTATION: Scheduler
        get() = Schedulers.computation()
}