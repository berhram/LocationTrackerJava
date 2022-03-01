package com.velvet.map.ui;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class MapViewModel extends MviViewModel<MapContract.View, MapViewState, MapViewEffect> implements MapContract.ViewModel {
    private final LocationNetwork locationRepo;
    private DateFilter filter = new DateFilter(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
    private final PublishSubject<Long> locationSubject = PublishSubject.create();

    public MapViewModel(LocationNetwork locationRepo) {
        this.locationRepo = locationRepo;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    locationSubject.flatMap(t -> locationRepo.getLocationsFromRemote().toObservable())
                            .flatMap(listResult -> Observable.fromIterable(listResult.data))
                            .filter(this::checkIfLocationMatchFilter)
                            .flatMap(location -> Observable.just(convertLocationToMarker(location)))
                            .subscribe(this::setMarker, e -> {
                                e.printStackTrace();
                                postErrorMessage();
                            }),
                    Observable.interval(Values.MAP_CHECK_FREQUENTLY_SEC, TimeUnit.SECONDS)
                            .subscribe()
            );
        }
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
        return filter.check(location.time);
    }

    public void setFilter(DateFilter filter) {
        this.filter = filter;
    }

    @Override
    protected MapViewState getDefaultState() {
        return MapViewState.createSetDefaultState();
    }
}