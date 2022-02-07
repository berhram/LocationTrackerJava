package com.velvet.models.cache;

import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.rxjava3.subjects.ReplaySubject;

public class ServiceCache implements Cache<MarkerOptions> {
    //TODO add DI
    private ReplaySubject<MarkerOptions> cache = ReplaySubject.create();

    @Override
    public void put(MarkerOptions marker) {
        cache.onNext(marker);
    }

    @Override
    public ReplaySubject<MarkerOptions> get() {
        return cache;
    }
}
