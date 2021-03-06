package com.velvet.map.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.core.Converters;
import com.velvet.core.Values;
import com.velvet.core.filter.DateFilter;
import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.core.models.database.SimpleLocation;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.libs.mvi.MviViewModel;
import com.velvet.map.ui.state.MapViewEffect;
import com.velvet.map.ui.state.MapViewState;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class MapViewModel extends MviViewModel<MapContract.View, MapViewState, MapViewEffect> implements MapContract.ViewModel {
    private final PublishSubject<DateFilter> filterSubject = PublishSubject.create();
    private final PublishSubject<Long> markerSubject = PublishSubject.create();
    private final PublishSubject<Long> authSubject = PublishSubject.create();
    private final LocationNetwork locationNetwork;
    private final AuthNetwork authRepo;
    private final DateFilter filter = DateFilter.createFullDateFilter(new Date(0), new Date(System.currentTimeMillis()));
    private boolean mapIsReady = false;

    public MapViewModel(LocationNetwork locationNetwork, AuthNetwork authRepo) {
        this.locationNetwork = locationNetwork;
        this.authRepo = authRepo;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner owner, @NonNull Lifecycle.Event event) {
        super.onStateChanged(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    markerSubject
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .filter(t -> mapIsReady)
                            .flatMap(t -> locationNetwork.downloadLocations().toObservable())
                            .flatMap(listResult -> Observable.fromIterable(listResult.data))
                            .filter(simpleLocation -> {
                                Log.d("LOC", " marker on filter");
                                return checkIfLocationMatchFilter(simpleLocation);
                            })
                            .flatMap(location -> Observable.just(convertLocationToMarker(location)))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(markerOptions -> {
                                if (markerOptions != null) {
                                    setMarker(markerOptions);
                                    Log.d("LOC", " marker set in viewmodel's method");
                                }
                            }, e -> {
                                e.printStackTrace();
                                setErrorMessage();
                            }),
                    filterSubject.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(inputFilter -> {
                                filter.updateFilter(inputFilter);
                                Log.d("LOC", " filter set in viewmodel's method");
                                setFilter();
                            }, Throwable::printStackTrace),
                    Observable.interval(Values.MAP_CHECK_FREQUENTLY_SEC, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(markerSubject::onNext, Throwable::printStackTrace),
                    authSubject.subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .flatMapCompletable(t -> authRepo.signOut())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::proceedToLoginScreen, Throwable::printStackTrace)
            );
            markerSubject.onNext(System.currentTimeMillis());
            filterSubject.onNext(filter);
        }
    }

    @Override
    protected MapViewState getDefaultState() {
        return MapViewState.createSetDefaultState();
    }

    @Override
    public void mapReadyCallback() {
        mapIsReady = true;
    }

    @Override
    public boolean getMapCallback() {
        return mapIsReady;
    }

    @Override
    public void updateFilter(DateFilter newFilter) {
        filterSubject.onNext(newFilter);
    }

    @Override
    public void signOut() {
        authSubject.onNext(System.currentTimeMillis());
    }

    private void proceedToLoginScreen() {
        setAction(MapViewEffect.proceedToLoginScreen());
    }

    private void setFilter() {
        setState(MapViewState.createSetFilterState(Converters.dateToString(filter.getStartDate()), Converters.dateToString(filter.getEndDate())));
    }

    private MarkerOptions convertLocationToMarker(SimpleLocation location) {
        return new MarkerOptions().position(new LatLng(location.latitude, location.longitude))
                .title(Converters.timeToString(location.time));
    }

    private void setErrorMessage() {
        setState(MapViewState.createSetErrorMessageState());
    }

    private void setMarker(MarkerOptions marker) {
        setState(MapViewState.createSetMarkerState(marker));
    }

    private boolean checkIfLocationMatchFilter(SimpleLocation location) {
        Log.d("LOC", " filter: " + filter.check(location.time) );
        return filter.check(location.time);
    }
}