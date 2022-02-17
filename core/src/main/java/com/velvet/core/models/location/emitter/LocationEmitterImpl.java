package com.velvet.core.models.location.emitter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.velvet.core.Values;
import com.velvet.core.result.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;


public class LocationEmitterImpl extends LocationCallback implements LocationEmitter {
    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;
    List<Location> locationList = new ArrayList<>();

    public LocationEmitterImpl(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void start() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(Values.LOCATION_WRITE_FREQUENTLY_MILLIS);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, this, null);
        }
    }

    @Override
    public Single<Result<List<Location>>> getLocations() {
        return Single.fromCallable(() -> {
            List<Location> outputed = locationList;
            locationList.clear();
            return Result.success(outputed);
        });
    }

    @Override
    public void onLocationResult(@NonNull LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        for (Location location : locationResult.getLocations()) {
            if (location != null) {
                locationList.add(location);
            }
        }
    }

    @Override
    public void stop() {
        fusedLocationClient.removeLocationUpdates(this);
    }
}
