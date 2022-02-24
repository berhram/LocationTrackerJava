package com.velvet.core.models.location;

import io.reactivex.rxjava3.core.Observable;

public interface LocationEmitter<T> {
    void start();
    void stop();
    Observable<T> getLocation();
}
