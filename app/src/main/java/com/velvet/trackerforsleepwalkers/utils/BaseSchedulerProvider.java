package com.velvet.trackerforsleepwalkers.utils;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Scheduler;

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
