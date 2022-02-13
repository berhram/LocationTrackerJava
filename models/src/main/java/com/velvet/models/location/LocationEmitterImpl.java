package com.velvet.models.location;

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
import com.velvet.models.cache.GlobalCache;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.multibindings.ClassKey;
import dagger.multibindings.StringKey;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class LocationEmitterImpl extends LocationCallback implements LocationEmitter {
    private final FusedLocationProviderClient fusedLocationClient;
    private final Context context;

    @Inject
    @ClassKey(Location.class)
    GlobalCache<Location> cache;

    public LocationEmitterImpl(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void start() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, this, null);
        }
    }

    @Override
    public void onLocationResult(@NonNull LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        for (Location location : locationResult.getLocations()) {
            if (location != null) {
                List<Location> tempList = new ArrayList<>();
                tempList.add(location);
                cache.addItems(tempList);
            }
        }
    }

    @Override
    public void stop() {
        fusedLocationClient.removeLocationUpdates(this);
    }
}
