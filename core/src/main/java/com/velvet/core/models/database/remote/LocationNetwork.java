package com.velvet.core.models.database.remote;

import com.velvet.core.models.database.local.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocationNetwork {
    Completable createLocationStorage();

    Completable checkIfLocationStorageExists();

    Completable saveLocationToRemote(SimpleLocation location);

    Completable saveLocationToLocal(Result<SimpleLocation> locationResult);

    Single<Result<List<SimpleLocation>>> getLocationsFromLocal();

    Single<Result<List<SimpleLocation>>> getLocationsFromRemote();

    Completable deleteLocationFromLocal(SimpleLocation location);
}
