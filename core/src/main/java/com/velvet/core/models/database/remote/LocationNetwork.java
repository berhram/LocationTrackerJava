package com.velvet.core.models.database.remote;

import android.location.Location;


import com.velvet.core.filter.DateFilter;
import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocationNetwork {
    Completable saveLocationToRemote(Location location);

    Completable saveLocationToLocal(Result<Location> locationResult);

    Single<List<Location>> getLocationsFromLocal();

    Single<List<Location>> getLocationsFromRemote(DateFilter filter);

    Completable deleteLocationFromLocal(Location location);
}
