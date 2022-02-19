package com.velvet.tracker;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.core.Values;
import com.velvet.core.cache.MessageCache;
import com.velvet.libs.mvi.MviViewModel;
import com.velvet.tracker.state.TrackerViewEffect;
import com.velvet.tracker.state.TrackerViewState;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrackerViewModel extends MviViewModel<TrackerContract.View, TrackerViewState, TrackerViewEffect> implements TrackerContract.ViewModel {
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                Observable.interval(Values.LOCATION_CHECK_FREQUENTLY_SEC, TimeUnit.SECONDS)
                        .flatMap(t -> Observable.fromIterable(messageCache.getItems()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setLastLocation, Throwable::printStackTrace)
            );
        }
    }

    @Override
    protected TrackerViewState getDefaultState() {
        return TrackerViewState.createInitialState();
    }

    @Inject
    MessageCache messageCache;

    @Override
    public void setLastLocation(String location) {
        Log.d("LOC", "TrackerViewModel setLastLocation invoked");
        setState(TrackerViewState.createSetLocationState(location));
    }
}
