package com.velvet.tracker.services;

import android.content.Context;
import android.location.Location;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.velvet.core.Values;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.database.local.LocalStorage;
import com.velvet.core.models.database.remote.RemoteDatabase;
import com.velvet.core.models.database.worker.SyncWorker;
import com.velvet.core.models.location.emitter.LocationEmitter;
import com.velvet.core.result.Result;
import com.velvet.tracker.di.DaggerTrackerComponent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class TrackerControllerImpl implements TrackerController {
    private final CompositeDisposable disposables = new CompositeDisposable();
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();
    OneTimeWorkRequest sync = new OneTimeWorkRequest.Builder(SyncWorker.class)
            .setConstraints(constraints)
            .build();

    @Inject
    WorkManager workManager;

    @Inject
    LocalStorage local;

    @Inject
    RemoteDatabase remote;

    @Inject
    MessageCache messageCache;

    @Inject
    LocationEmitter emitter;

    public TrackerControllerImpl(Context ctx) {
        DaggerTrackerComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(ctx)).build().inject(this);
    }

    @Override
    public void start() {
        disposables.add(
            Observable.interval(Values.LOCATION_CHECK_FREQUENTLY_SEC, TimeUnit.SECONDS)
                    .flatMap(t -> emitter.getLocation())
                    .filter(locationResult -> {
                        if (locationResult.isError()) {
                            messageCache.addItem(Result.error((Exception) locationResult.error));
                            return false;
                        } else {
                            return true;
                        }
                    })
                    .flatMap(locationResult -> {
                        if (!remote.saveLocation(locationResult.data)) {
                            workManager.enqueue(sync);
                            return local.addLocation(locationResult).toObservable();
                        } else
                            return Observable.just(locationResult);
                    })
                    .subscribe(locationResult -> {
                            messageCache.addRawDate(new Date(locationResult.data.getTime()));
                    })
        );
    }

    @Override
    public void stop() {
        disposables.clear();
    }
}
