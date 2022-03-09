package com.velvet.tracker.services.controller;

import android.util.Log;

import com.velvet.core.Converters;
import com.velvet.core.models.cache.Cache;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.core.models.location.LocationEmitter;
import com.velvet.core.result.Result;
import com.velvet.tracker.model.work.SyncWorkManager;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrackerController implements ServiceController {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final SyncWorkManager workManager;
    private final LocationNetwork locationRepo;
    private final Cache cache;
    private final LocationEmitter emitter;

    public TrackerController(SyncWorkManager workManager, LocationNetwork locationRepo, Cache cache, LocationEmitter emitter) {
        this.workManager = workManager;
        this.locationRepo = locationRepo;
        this.cache = cache;
        this.emitter = emitter;
    }

    @Override
    public void start() {
        emitter.start();
        disposables.add(
            emitter.getLocation()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .filter(locationResult -> {
                        if (locationResult.isError()) {
                            cache.addItem(Result.error((Exception) locationResult.error));
                            Log.d("LOC", locationResult.error.toString() + " failure filter");
                            return false;
                        } else {
                            cache.addItem(Result.success(Converters.timeToString(locationResult.data.time)));
                            Log.d("LOC", Converters.timeToString(locationResult.data.time) + " success filter");
                            return true;
                        }
                    })
                    .flatMapCompletable(locationResult -> locationRepo.uploadLocation(locationResult.data)
                            .onErrorResumeWith(locationRepo.saveLocation(locationResult.data)))
                    .subscribe(() -> {
                                Log.d("LOC", " work scheduled");
                                workManager.scheduleSyncTask();
                            },
                            Throwable::printStackTrace)
        );
    }

    @Override
    public void stop() {
        emitter.stop();
        disposables.clear();
    }
}
