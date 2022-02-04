package com.velvet.models.location;

import android.location.Location;

import io.reactivex.rxjava3.subjects.PublishSubject;

public interface LocationEmitterInterface {
    void start();
    void stop();
    PublishSubject<Location> getLocation();
}
