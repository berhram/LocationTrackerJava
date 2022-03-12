package com.velvet.tracker.services.controller;

import com.velvet.core.Converters;
import com.velvet.core.models.cache.Cache;
import com.velvet.core.models.database.local.LocalRepository;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.core.models.location.LocationEmitter;
import com.velvet.core.result.Result;
import com.velvet.tracker.model.work.SyncWorkManager;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrackerController implements ServiceController {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final SyncWorkManager workManager;
    private final LocationNetwork remoteRepo;
    private final LocalRepository localRepo;
    private final Cache cache;
    private final LocationEmitter emitter;

    public TrackerController(SyncWorkManager workManager, LocationNetwork remoteRepo, LocalRepository localRepo, Cache cache, LocationEmitter emitter) {
        this.workManager = workManager;
        this.localRepo = localRepo;
        this.remoteRepo = remoteRepo;
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
                            return false;
                        } else {
                            cache.addItem(Result.success(Converters.timeToString(locationResult.data.time)));
                            return true;
                        }
                    })
                    .flatMapCompletable(locationResult -> remoteRepo.uploadLocation(locationResult.data).onErrorResumeWith(localRepo.saveLocation(locationResult.data).andThen(scheduleSync())))
                    .subscribe()
        );
    }

    private Completable scheduleSync() {
        return Completable.fromCallable(() -> {
            workManager.scheduleSyncTask();
            return Completable.complete();
        });
    }

    @Override
    public void stop() {
        emitter.stop();
        disposables.clear();
    }
}
