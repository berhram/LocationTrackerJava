package com.velvet.parentapp.ui.map;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.childapp.models.data.LocationRepository;
import com.velvet.sharedmodule.mvi.MviViewModel;

public class MapViewModel extends MviViewModel<MapContract.View, MapViewState, MapViewEffect> implements MapContract.ViewModel {

    private final LocationRepository locationRepository;

    public MapViewModel(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy();
        }
    }

    @Override
    protected MapViewState getDefaultState() {
        return MapViewState.createSetMarkersState();
    }

}