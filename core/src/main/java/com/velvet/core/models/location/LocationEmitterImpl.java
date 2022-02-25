package com.velvet.core.models.location;

import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.velvet.core.Values;
import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class LocationEmitterImpl extends LocationCallback implements LocationEmitter {
    private final FusedLocationProviderClient fusedLocationClient;
    private final BehaviorSubject<Result<Location>> lastLocation = BehaviorSubject.create();
    private final LocationRequest locationRequest = LocationRequest.create();

    public LocationEmitterImpl(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void start() {
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(Values.LOCATION_CHECK_FREQUENTLY_MILLIS);
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, this, Looper.getMainLooper());
        } catch (SecurityException e) {
            e.printStackTrace();
            lastLocation.onNext(Result.error(e));
        }

    }

    @Override
    public Observable<Result<Location>> getLocation() {
        return lastLocation;
    }

    @Override
    public void onLocationResult(@NonNull LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        lastLocation.onNext(Result.success(locationResult.getLastLocation()));
    }

    @Override
    public void stop() {
        fusedLocationClient.removeLocationUpdates(this);
    }
}
