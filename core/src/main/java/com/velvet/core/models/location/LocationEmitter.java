package com.velvet.core.models.location;

import com.velvet.core.models.database.local.SimpleLocation;
import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Observable;

public interface LocationEmitter {
    void start();
    void stop();
    Observable<Result<SimpleLocation>> getLocation();
}
