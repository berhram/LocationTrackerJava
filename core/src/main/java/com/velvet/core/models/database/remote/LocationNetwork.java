package com.velvet.core.models.database.remote;

import android.location.Location;


import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocationNetwork {
    Single<Result<Location>> saveLocationToRemote(Location location);

    Completable saveLocationToLocal(Result<Location> locationResult);

    Single<List<Location>> getLocationsFromLocal();

    Single<List<Location>> getLocationsFromRemote();

    void deleteLocationFromLocal(Location location);
}
