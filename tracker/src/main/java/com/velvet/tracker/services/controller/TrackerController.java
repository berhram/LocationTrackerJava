package com.velvet.tracker.services.controller;

import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.tracker.model.work.SyncWorkManager;
import com.velvet.core.models.location.LocationEmitter;
import com.velvet.core.result.Result;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrackerController implements ServiceController {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final SyncWorkManager workManager;
    private final LocationNetwork locationRepo;
    private final MessageCache cache;
    private final LocationEmitter emitter;

    public TrackerController(SyncWorkManager workManager, LocationNetwork locationRepo, MessageCache cache, LocationEmitter emitter) {
        this.workManager = workManager;
        this.locationRepo = locationRepo;
        this.cache = cache;
        this.emitter = emitter;
    }

    @Override
    public void start() {
        disposables.add(
            emitter.getLocation()
                    .subscribeOn(Schedulers.io())
                    .filter(locationResult -> {
                        if (locationResult.isError()) {
                            cache.addItem(Result.error((Exception) locationResult.error));
                            return false;
                        } else {
                            return true;
                        }
                    })
                    .flatMap(locationResult -> locationRepo.saveLocationToRemote(locationResult.data).toObservable())
                    .filter(Result::isError)
                    .flatMap(locationResult -> {
                            workManager.doSyncWork(locationRepo);
                            return locationRepo.saveLocationToLocal(locationResult).toObservable();
                    })
                    .subscribe()
        );
    }

    @Override
    public void stop() {
        disposables.clear();
    }
}
