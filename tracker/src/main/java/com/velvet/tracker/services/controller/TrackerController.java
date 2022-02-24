package com.velvet.tracker.services.controller;

import android.location.Location;

import com.velvet.core.models.cache.LocationCache;
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
    private final LocationCache cache;
    private final LocationEmitter<Result<Location>> emitter;

    public TrackerController(SyncWorkManager workManager, LocationNetwork locationRepo, LocationCache cache, LocationEmitter<Result<Location>> emitter) {
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
                    .flatMapCompletable(locationRepo::saveLocationToLocal)
                    .subscribe(() -> {
                        workManager.doSyncWork(locationRepo);
                    })
        );
    }

    @Override
    public void stop() {
        emitter.stop();
        disposables.clear();
    }
}
