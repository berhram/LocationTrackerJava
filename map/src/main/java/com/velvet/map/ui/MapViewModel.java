package com.velvet.map.ui;

import android.location.Location;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.map.ui.state.MapViewEffect;
import com.velvet.map.ui.state.MapViewState;
import com.velvet.models.cache.GlobalCache;
import com.velvet.models.location.LocationReceiver;
import com.velvet.models.result.Result;
import com.velvet.mvi.MviViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class MapViewModel extends MviViewModel<MapContract.View, MapViewState, MapViewEffect> implements MapContract.ViewModel {
    @Inject
    LocationReceiver receiver;

    private final List<Location> lastLocations;

    public MapViewModel() {
        this.lastLocations = new ArrayList<>();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    Observable.interval(20, TimeUnit.MINUTES)
                            .flatMap(t -> receiver.getLocations().toObservable())
                            .map(listResult -> listResult.data)
                            .flatMap(Observable::fromIterable)
                            .filter(this::checkIfUpdateNeed)
                            .map(this::convertLocationToMarker)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::setMarker)
            );
        }
    }
    private MarkerOptions convertLocationToMarker(Location location) {
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
        return new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title(sDF.format(new Date(location.getTime())));

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