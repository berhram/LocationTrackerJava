package com.velvet.tracker.model.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.tracker.di.DaggerTrackerComponent;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SyncWorker extends Worker {
    CompositeDisposable disposables = new CompositeDisposable();
    @Inject
    LocationNetwork locationRepo;

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        DaggerTrackerComponent.builder()
                .coreComponent(CoreInjectHelper.provideCoreComponent(getApplicationContext()))
                .build().inject(this);
    }

    @NonNull
    @Override
    public Result doWork() {
        final Result[] flag = {Result.success()};
        disposables.add(
                locationRepo.getLocationsFromLocal()
                    .toObservable()
                    .flatMap(Observable::fromIterable)
                    .flatMap(location -> locationRepo.saveLocationToRemote(location).toObservable())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(locationResult -> {
                        if (locationResult.isError()) {
                            flag[0] = Result.failure();
                        } else {
                            locationRepo.deleteLocationFromLocal(locationResult.data);
                        }
                    })
        );
        return flag[0];
    }
}
