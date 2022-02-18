package com.velvet.core.models.location.emitter;

import android.location.Location;

import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class LocationEmitterDecorator implements LocationEmitter {
    private final LocationEmitter locationEmitter;

    public LocationEmitterDecorator(LocationEmitter locationEmitter) {
        this.locationEmitter = locationEmitter;
    }

    @Override
    public void start() {
        locationEmitter.start();
    }

    @Override
    public void stop() {
        locationEmitter.start();
    }

    @Override
    public Single<Result<List<Location>>> getLocations() {
        return locationEmitter.getLocations();
    }
}