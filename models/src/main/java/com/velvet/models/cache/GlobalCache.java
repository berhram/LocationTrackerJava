package com.velvet.models.cache;

import android.location.Location;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GlobalCache {
    void addMarkers(List<MarkerOptions> inputMarkers);

    void addLocations(List<Location> inputLocations);

    Observable<List<MarkerOptions>> getMarkersObservable();

    Observable<List<Location>> getLocationsObservable();

    List<MarkerOptions> getMarkers();

    List<Location> getLocations();
}
