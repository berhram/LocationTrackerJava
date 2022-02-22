package com.velvet.core.models.database.local;

import android.location.Location;

import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LocalStorage {
    Single<Result<Location>> addLocation(Result<Location> locationResult);

    List<Location> getLocations();

    void deleteLocation(Location location);
}
