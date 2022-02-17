package com.velvet.core.models.location.receiver;

import android.location.Location;

import com.velvet.core.result.Result;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public interface LocationReceiver {
    @NonNull Single<Result<List<Location>>> getLocations();
}
