package com.velvet.core.models.location.emitter;

import android.location.Location;

import com.velvet.core.result.Result;

public interface LocationEmitter {
    void start();
    void stop();
    Result<Location> getLocation();
}
