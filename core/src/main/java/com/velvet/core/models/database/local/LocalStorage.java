package com.velvet.core.models.database.local;

import android.location.Location;

import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Single;

public interface LocalStorage {
    Single<Result<Location>> addLocation(Result<Location> locationResult);
}
