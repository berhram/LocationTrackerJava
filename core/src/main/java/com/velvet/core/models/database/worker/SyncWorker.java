package com.velvet.core.models.database.worker;

import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.velvet.core.models.database.local.LocalStorage;
import com.velvet.core.models.database.remote.RemoteDatabase;

import javax.inject.Inject;

public class SyncWorker extends Worker {
    @Inject
    LocalStorage local;

    @Inject
    RemoteDatabase remote;

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        //TODO add di to worker
    }

    @NonNull
    @Override
    public Result doWork() {
        for (Location location:
             local.getLocations()) {
            remote.saveLocation(location);
            local.deleteLocation(location);
        }
        return Result.success();
    }
}
