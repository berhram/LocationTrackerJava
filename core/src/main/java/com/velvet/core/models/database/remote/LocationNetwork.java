package com.velvet.core.models.database.remote;

import com.velvet.core.models.database.local.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocationNetwork {
    Completable saveLocationToRemote(List<SimpleLocation> locationList);

    Completable saveSingleLocationToRemote(SimpleLocation location);

    Completable saveLocationToLocal(List<SimpleLocation> locationList);

    Completable saveSingleLocationToLocal(SimpleLocation location);

    Single<Result<List<SimpleLocation>>> getLocationsFromLocal();

    Single<Result<List<SimpleLocation>>> getLocationsFromRemote();

    Completable deleteLocationFromLocal(List<SimpleLocation> locationList);
}
