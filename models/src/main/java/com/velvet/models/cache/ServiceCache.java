package com.velvet.models.cache;

import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.rxjava3.subjects.ReplaySubject;

public class ServiceCache implements Cache<MarkerOptions> {
    private static final ReplaySubject<MarkerOptions> cache =  ReplaySubject.create();

    public void put(MarkerOptions marker) {
        cache.onNext(marker);
    }

    public ReplaySubject<MarkerOptions> get() {
        return cache;
    }
}
