package com.velvet.models.cache;

import android.location.Location;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class GlobalCacheImpl implements GlobalCache{
    private final BehaviorSubject<List<MarkerOptions>> cachedMarkers = BehaviorSubject.createDefault(new ArrayList<>());
    private final BehaviorSubject<List<Location>> cachedLocations = BehaviorSubject.createDefault(new ArrayList<>());

    @Override
    public void addMarkers(List<MarkerOptions> inputMarkers) {
        final List<MarkerOptions> oldMarkers = cachedMarkers.getValue();
        final List<MarkerOptions> newMarkers = new ArrayList<>(inputMarkers.size() + oldMarkers.size());
        newMarkers.addAll(oldMarkers);
        newMarkers.addAll(inputMarkers);
        cachedMarkers.onNext(newMarkers);
    }

    @Override
    public void addLocations(List<Location> inputLocations) {
        final List<Location> oldLocations = cachedLocations.getValue();
        final List<Location> newLocations = new ArrayList<>(inputLocations.size() + oldLocations.size());
        newLocations.addAll(oldLocations);
        newLocations.addAll(inputLocations);
        cachedLocations.onNext(newLocations);
    }

    @Override
    public Observable<List<MarkerOptions>> getMarkersObservable() {
        return cachedMarkers;
    }

    @Override
    public Observable<List<Location>> getLocationsObservable() {
        return cachedLocations;
    }

    @Override
    public List<MarkerOptions> getMarkers() {
        return cachedMarkers.getValue();
    }

    @Override
    public List<Location> getLocations() {
        return cachedLocations.getValue();
    }
}
