package com.velvet.core.models.database.remote;

import android.content.Context;
import android.location.Location;

import androidx.room.Room;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.velvet.core.Values;
import com.velvet.core.models.database.local.Converters;
import com.velvet.core.models.database.local.LocalDatabase;
import com.velvet.core.models.database.local.LocationDao;
import com.velvet.core.models.database.local.LocationEntity;
import com.velvet.core.result.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirebaseLocationNetwork implements LocationNetwork {
    private final DatabaseReference remoteDatabase = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
    private final SimpleDateFormat sDF = new SimpleDateFormat(Values.DATE_PATTERN);
    private final LocalDatabase localDatabase;
    private final LocationDao dao;

    public FirebaseLocationNetwork(Context ctx) {
        localDatabase = Room.databaseBuilder(ctx, LocalDatabase.class, "Local storage").build();
        dao = localDatabase.locationDao();
    }

    public Single<Result<Location>> saveLocationToRemote(Location location) {
        return Single.fromCallable(() -> {
            final Task<Void> task = remoteDatabase.child("location").child(sDF.format(new Date(location.getTime()))).push().setValue(location);
            if (task.isSuccessful()) {
                return Result.success(location);
            } else {
                return new Result<>(location, task.getException());
            }
        });
    }

    public Completable saveLocationToLocal(Result<Location> locationResult) {
        return Completable.fromRunnable(() -> {
            dao.insert(Converters.locationToEntity(locationResult.data));
        });
    }

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

    public Single<List<Location>> getLocationsFromRemote() {
        //TODO fix it
        return Single.just(dao.getAll());
    }

    public void deleteLocationFromLocal(Location location) {
        dao.delete(Converters.locationToEntity(location));
    }
}