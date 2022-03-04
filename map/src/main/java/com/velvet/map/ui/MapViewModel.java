package com.velvet.map.ui;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.core.Converters;
import com.velvet.core.Values;
import com.velvet.core.filter.DateFilter;
import com.velvet.core.models.database.local.SimpleLocation;
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
    private final LocationNetwork locationRepo;
    private final DateFilter filter = DateFilter.createFullDateFilter(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
    private boolean mapIsReady = false;

    public MapViewModel(LocationNetwork locationRepo) {
        this.locationRepo = locationRepo;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    markerSubject
                            .subscribeOn(Schedulers.io())
                            .filter(t -> mapIsReady)
                            .flatMap(t -> locationRepo.getLocationsFromRemote().toObservable())
                            .flatMap(listResult -> Observable.fromIterable(listResult.data))
                            .filter(this::checkIfLocationMatchFilter)
                            .flatMap(location -> Observable.just(convertLocationToMarker(location)))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::setMarker, e -> {
                                e.printStackTrace();
                                postErrorMessage();
                            }),
                    filterSubject.subscribeOn(Schedulers.io()).subscribe(inputFilter -> {
                        filter.updateFilter(inputFilter);
                        markerSubject.onNext(System.currentTimeMillis());
                        setFilter();
                    }, Throwable::printStackTrace),
                    Observable.interval(Values.MAP_CHECK_FREQUENTLY_SEC, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(markerSubject::onNext, Throwable::printStackTrace)
            );
        }
    }

    @Override
    protected MapViewState getDefaultState() {
        return MapViewState.createSetDefaultState();
    }

    @Override
    public void mapReadyCallback() {
        mapIsReady = true;
        //setFilter();
    }

    @Override
    public void updateFilter(DateFilter filter) {
        filterSubject.onNext(filter);
    }

    private void setFilter() {
        setState(MapViewState.createSetFilterState(Converters.dateToString(filter.getStartDate()), Converters.dateToString(filter.getEndDate())));
    }

    private MarkerOptions convertLocationToMarker(SimpleLocation location) {
        return new MarkerOptions().position(new LatLng(location.latitude, location.longitude))
                .title(Converters.timeToString(location.time));
    }

    private void postErrorMessage() {
        setAction(MapViewEffect.postErrorMessage());
    }

    private void setMarker(MarkerOptions marker) {
        setState(MapViewState.createSetMarkerState(marker));
    }

    private boolean checkIfLocationMatchFilter(SimpleLocation location) {
        Log.d("LOC", Converters.timeToString(location.time) + "filtered");
        return filter.check(location.time);
    }
}