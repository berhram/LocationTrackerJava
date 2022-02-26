package com.velvet.core.models.database.remote;

import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velvet.core.Values;
import com.velvet.core.filter.DateFilter;
import com.velvet.core.models.database.local.Converters;
import com.velvet.core.models.database.local.LocalDatabase;
import com.velvet.core.models.database.local.LocationDao;
import com.velvet.core.models.database.local.LocationEntity;
import com.velvet.core.result.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public Completable saveLocationToRemote(Location location) {
        final Task<Void> task = remoteDatabase.child("location")
                .child(Converters.timeToString(location.getTime()))
                .child("latitude")
                .setValue(location.getLatitude())
                .continueWithTask(voidTask -> remoteDatabase.child("location")
                .child(Converters.timeToString(location.getTime()))
                .child("longitude")
                .setValue(location.getLongitude()));
        if (task.isSuccessful()) {
            return Completable.complete();
        } else {
            return Completable.error(task.getException());
        }
    }

    @Override
    public Completable saveLocationToLocal(Result<Location> locationResult) {
        return Completable.fromRunnable(() -> {
            dao.insert(LocationEntity.locationToEntity(locationResult.data));
        });
    }

    @Override
    public Single<List<Location>> getLocationsFromLocal() {
        return Single.fromCallable(() -> {
            List<Location> output = new ArrayList<>();
            for (LocationEntity entity:
                    dao.getAll()) {
                output.add(Converters.entityToLocation(entity));
            }
            return output;
        });
    }

    @Override
    public Single<List<Location>> getLocationsFromRemote(DateFilter filter) {
        return Single.fromCallable(() -> {
            //TODO fix it later
            List<Location> output = new ArrayList<>();
            return output;
        });
    }

    @Override
    public void deleteLocationFromLocal(Location location) {
        dao.delete(Converters.locationToEntity(location));
    }
}
