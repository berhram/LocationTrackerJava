package com.velvet.core.models.database.remote;

import com.velvet.core.models.database.SimpleLocation;
import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocationNetwork {
    Completable uploadLocations(List<SimpleLocation> locationList);

    Completable uploadLocation(SimpleLocation location);

    Single<Result<List<SimpleLocation>>> downloadLocations();
}
