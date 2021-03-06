package com.velvet.core.models.database.local;

import android.content.Context;

import androidx.room.Room;

import com.velvet.core.models.database.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class LocalRepositoryImpl implements LocalRepository {
    private final LocalDatabase localDatabase;
    private final LocationDao dao;

    public LocalRepositoryImpl(Context ctx) {
        localDatabase = Room.databaseBuilder(ctx, LocalDatabase.class, "Local storage").build();
        dao = localDatabase.locationDao();
    }

    @Override
    public Completable saveLocation(SimpleLocation location) {
        return Completable.fromCallable(() -> {
            dao.insert(location);
            return Completable.complete();
        });
    }

    @Override
    public Single<Result<List<SimpleLocation>>> getLocations() {
        return Single.fromCallable(() -> Result.success(dao.getAll()));
    }

    @Override
    public Completable deleteLocations(List<SimpleLocation> locationList) {
        return Completable.fromCallable(() -> {
            dao.deleteAll(locationList);
            return Completable.complete();
        });
    }
}
