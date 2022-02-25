package com.velvet.core.models.location;

import android.location.Location;

import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Observable;

public interface LocationEmitter {
    void start();
    void stop();
    Observable<Result<Location>> getLocation();
}
