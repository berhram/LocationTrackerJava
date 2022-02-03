package com.velvet.childapp.models.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.LocationSource;
import com.velvet.childapp.models.preferences.SharedPreferenceProvider;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class LocationEmitter implements LocationSource, LocationListener {
    private final PublishSubject<Location> locationSubject = PublishSubject.create();
    private OnLocationChangedListener locationChangedListener;
    private final LocationManager locationManager;
    private final String source;
    private final long MIN_TIME_MS = 10_000;
    private final float MIN_DISTANT_M = 0;

    public LocationEmitter(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        source = new SharedPreferenceProvider(context).get("Source", "");
    }

    @Override
    public void activate(@NonNull OnLocationChangedListener listener) {
        this.locationChangedListener = listener;
        if (!source.equals("")) {
            locationManager.requestLocationUpdates(source, MIN_TIME_MS, MIN_DISTANT_M, this::onLocationChanged);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (locationChangedListener != null) {
            locationSubject.onNext(location);
        }
    }

    @Override
    public void deactivate() {
        locationManager.removeUpdates(this::onLocationChanged);
    }

    public PublishSubject<Location> getLocationSubject() {
        return locationSubject;
    }
}
