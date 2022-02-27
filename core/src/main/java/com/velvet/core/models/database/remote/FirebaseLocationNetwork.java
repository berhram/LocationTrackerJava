package com.velvet.core.models.database.remote;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velvet.core.models.database.local.LocalDatabase;
import com.velvet.core.models.database.local.LocationDao;
import com.velvet.core.models.database.local.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseLocationNetwork implements LocationNetwork {
    private final DatabaseReference remoteDatabase = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
    private final LocalDatabase localDatabase;
    private final LocationDao dao;

    public FirebaseLocationNetwork(Context ctx) {
        localDatabase = Room.databaseBuilder(ctx, LocalDatabase.class, "Local storage").build();
        dao = localDatabase.locationDao();
    }

    @Override
    public Completable createLocationStorage() {
        return Completable.fromCallable(() -> {
            Task<Void> task = remoteDatabase.child("locations").setValue(new ArrayList<SimpleLocation>());
            if (task.isSuccessful()) {
                return Completable.complete();
            } else {
                return Completable.error(task.getException());
            }
        });
    }

    @Override
    public Completable saveLocationToRemote(SimpleLocation location) {
            return Completable.fromCallable(() -> {
                Task getTask = remoteDatabase.child("locations").get();
                if (getTask.isSuccessful()) {
                    List<SimpleLocation> list = (List<SimpleLocation>) getTask.getResult();
                    list.add(location);
                    Task setTask = remoteDatabase.child("locations").setValue(list);
                    if (setTask.isSuccessful()) {
                        return Completable.complete();
                    } else {
                        return Completable.error(setTask.getException());
                    }
                } else {
                    return Completable.error(getTask.getException());
                }
            });
    }

    @Override
    public Completable saveLocationToLocal(Result<SimpleLocation> locationResult) {
        return Completable.fromRunnable(() -> dao.insert(locationResult.data));
    }

    @Override
    public Single<Result<List<SimpleLocation>>> getLocationsFromLocal() {
        return Single.fromCallable(() -> Result.success(dao.getAll()));
    }

    @Override
    public Single<Result<List<SimpleLocation>>> getLocationsFromRemote() {
        return Single.fromCallable(() -> {
            Task<DataSnapshot> task = remoteDatabase.child("locations").get();
            if (task.isSuccessful()) {
                return Result.success((List) task.getResult().getValue());
            } else {
                return Result.error(task.getException());
            }
        });
    }

    @Override
    public Completable deleteLocationFromLocal(SimpleLocation location) {
        return Completable.fromCallable(() -> {
            dao.delete(location);
            return Completable.complete();
        });
    }
}
