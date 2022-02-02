package com.velvet.trackerforsleepwalkers.ui.map;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.trackerforsleepwalkers.R;
import com.velvet.trackerforsleepwalkers.models.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.models.auth.FirebaseAuthMessages;
import com.velvet.trackerforsleepwalkers.models.data.LocationRepository;
import com.velvet.trackerforsleepwalkers.mvi.MviViewModel;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

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