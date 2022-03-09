package com.velvet.core.models.location;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.velvet.core.Values;
import com.velvet.core.Converters;
import com.velvet.core.models.database.local.SimpleLocation;
import com.velvet.core.result.Result;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class LocationEmitterImpl extends LocationCallback implements LocationEmitter {
    private final FusedLocationProviderClient fusedLocationClient;
    private final BehaviorSubject<Result<SimpleLocation>> lastLocation = BehaviorSubject.create();
    private final LocationRequest locationRequest = LocationRequest.create();

    public LocationEmitterImpl(Context context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void start() {
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(Values.LOCATION_CHECK_FREQUENTLY_MILLIS);
        locationRequest.setFastestInterval(Values.LOCATION_CHECK_FREQUENTLY_MILLIS);
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, this, Looper.getMainLooper());
        } catch (SecurityException e) {
            e.printStackTrace();
            lastLocation.onNext(Result.error(e));
        }
    }

    @Override
    public Observable<Result<SimpleLocation>> getLocation() {
        return lastLocation.hide();
    }

    @Override
    public void onLocationResult(@NonNull LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        Log.d("LOC", "onLocationResult: success");
        lastLocation.onNext(Result.success(Converters.locationToSimpleLocation(locationResult.getLastLocation())));
    }

    @Override
    public void stop() {
        fusedLocationClient.removeLocationUpdates(this);
    }
}
