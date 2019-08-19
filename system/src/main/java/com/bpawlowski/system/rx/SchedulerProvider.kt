package com.bpawlowski.system.rx

import io.reactivex.Scheduler

interface SchedulerProvider{
    val MAIN: Scheduler
    val IO: Scheduler
    val COMPUTATION: Scheduler
}