package com.velvet.tracker.ui;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.core.models.cache.Cache;
import com.velvet.core.models.cache.CacheImpl;
import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.libs.mvi.MviViewModel;
import com.velvet.tracker.ui.state.TrackerViewEffect;
import com.velvet.tracker.ui.state.TrackerViewState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class TrackerViewModel extends MviViewModel<TrackerContract.View, TrackerViewState, TrackerViewEffect> implements TrackerContract.ViewModel {
    private final PublishSubject<Long> authSubject = PublishSubject.create();
    private final Cache locationCache;
    private final AuthNetwork authRepo;


    public TrackerViewModel(Cache locationCache, AuthNetwork authRepo) {
        this.locationCache = locationCache;
        this.authRepo = authRepo;
    }

    @Override
    public void onStateChanged(LifecycleOwner owner, Lifecycle.Event event) {
        super.onStateChanged(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    locationCache.getItem()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            Log.d("LOC", s.data + " and e: " + s.error + " was receive from cache");
                            if (s.isError()) {
                                Log.d("LOC", s.error.toString() + " was receive from cache");
                                setError(s.error.toString());
                            } else {
                                Log.d("LOC", s.data + " was receive from cache");
                                setLastLocation(s.data);
                            }
                        }, Throwable::printStackTrace),
                    authSubject.flatMapCompletable(t -> authRepo.signOut())
                            .subscribe(() -> {}, Throwable::printStackTrace)
            );
        }
    }

    @Override
    protected TrackerViewState getDefaultState() {
        return TrackerViewState.createInitialState();
    }

    @Override
    public void signOut() {
        authSubject.onNext(System.currentTimeMillis());
    }

    @Override
    public void setLastLocation(String location) {
        setState(TrackerViewState.createSetLocationState(location));
    }

    @Override
    public void setError(String location) {
        setState(TrackerViewState.createSetErrorState(location));
    }
}
