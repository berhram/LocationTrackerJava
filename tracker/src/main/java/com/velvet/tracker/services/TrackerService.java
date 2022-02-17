package com.velvet.tracker.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.velvet.core.Values;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.location.emitter.LocationEmitter;
import com.velvet.core.models.location.emitter.LocationEmitterDatabase;
import com.velvet.core.models.location.emitter.LocationEmitterImpl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrackerService extends Service {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final LocationEmitter emitter;

    @Inject
    MessageCache messageCache;

    public TrackerService() {
        this.emitter = new LocationEmitterDatabase(new LocationEmitterImpl(this));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        disposables.add(
                Observable.interval(Values.LOCATION_READ_FREQUENTLY_SEC, TimeUnit.SECONDS)
                        .flatMap(t -> emitter.getLocations().toObservable())
                        .map(listResult -> listResult.data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(locations -> {
                            messageCache.addRawDate(new Date(locations.get(locations.size()-1).getTime()));
                        })
        );
    }

    @Override
    public void onDestroy() {
        disposables.clear();
    }


}