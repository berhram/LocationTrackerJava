package com.velvet.core.models.database.local;

import android.content.Context;
import android.location.Location;

import androidx.room.Room;

import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Single;

public class LocalStorageImpl implements LocalStorage {
    private final LocalDatabase db;
    private final LocationDao dao;


    public LocalStorageImpl(Context ctx) {
        db = Room.databaseBuilder(ctx, LocalDatabase.class, "Local storage").build();
        dao = db.locationDao();
    }

    public Single<Result<Location>> addLocation(Result<Location> locationResult) {
        return Single.fromCallable(() -> {
            dao.insert(locationResult.data);
            return locationResult;
        });
    }
}
