package com.example.bpawlowski.falldetector.service.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SchedulerProvider @Inject constructor() : ISchedulerProvider {
    override val MAIN: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val IO: Scheduler
        get() = Schedulers.io()
    override val COMPUTATION: Scheduler
        get() = Schedulers.computation()
}