package com.velvet.models.location;

import android.location.Location;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public interface LocationEmitter {
    void start();
    void stop();
    Observable<Location> getLocation();
}
