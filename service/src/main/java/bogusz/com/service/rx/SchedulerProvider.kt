package bogusz.com.service.rx

import io.reactivex.Scheduler

interface SchedulerProvider{
    val MAIN: Scheduler
    val IO: Scheduler
    val COMPUTATION: Scheduler
}