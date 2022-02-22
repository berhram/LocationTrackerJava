package com.velvet.core.models.location;

import android.location.Location;

import com.velvet.core.result.Result;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public interface LocationEmitter {
    void start();
    void stop();
    BehaviorSubject<Result<Location>> getLocation();
}
