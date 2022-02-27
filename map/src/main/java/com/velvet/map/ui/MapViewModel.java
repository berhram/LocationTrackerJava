package com.velvet.map.ui;

import android.location.Location;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.core.Converters;
import com.velvet.libs.mvi.MviViewModel;
import com.velvet.map.ui.state.MapViewEffect;
import com.velvet.map.ui.state.MapViewState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapViewModel extends MviViewModel<MapContract.View, MapViewState, MapViewEffect> implements MapContract.ViewModel {

    private final List<Location> lastLocations = new ArrayList<>();

    public MapViewModel() {
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(

            );
        }
    }
    private MarkerOptions convertLocationToMarker(Location location) {

        return new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title(Converters.dateToString(new Date(location.getTime())));

    }

    @Override
    protected MapViewState getDefaultState() {
        return MapViewState.createSetDefaultState();
    }

    private boolean checkIfUpdateNeed(Location location) {
        if (!lastLocations.contains(location)) {
            lastLocations.add(location);
            return true;
        } else {
            return false;
        }
    }

    private void setMarker(MarkerOptions marker) {
        setState(MapViewState.createSetMarkerState(marker));
    }
}