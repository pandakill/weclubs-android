package com.mm.weclubs.util.rx;

import io.reactivex.Scheduler;


public interface SchedulerProvider {

    /**
     * UI线程
     */
    Scheduler ui();


    Scheduler computation();

    /**
     * IO线程
     */
    Scheduler io();

}
