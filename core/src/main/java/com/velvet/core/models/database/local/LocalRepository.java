package com.velvet.core.models.database.local;

import com.velvet.core.models.database.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocalRepository {

    Completable saveLocation(SimpleLocation location);

    Single<Result<List<SimpleLocation>>> getLocations();

    Completable deleteLocations(List<SimpleLocation> locationList);
}
