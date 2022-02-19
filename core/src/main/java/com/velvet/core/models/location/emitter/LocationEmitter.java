package com.velvet.core.models.location.emitter;

import android.location.Location;

import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LocationEmitter {
    void start();
    void stop();
    Result<Location> getLocation();
}
