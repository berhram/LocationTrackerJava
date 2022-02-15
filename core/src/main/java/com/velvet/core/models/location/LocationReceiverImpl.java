package com.velvet.core.models.location;

import android.location.Location;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.velvet.core.result.Result;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LocationReceiverImpl implements LocationReceiver {
    @Inject
    DatabaseReference database;

    public Single<Result<List<Location>>> getLocations() {
        return Single.fromCallable(() -> {
                    final Task<DataSnapshot> task = database.child("Locations").get();
                    if (task.isSuccessful()) {
                        return Result.success((List<Location>) task.getResult().getValue());
                    } else {
                        return Result.error(task.getException());
                    }
        });
    }
}
