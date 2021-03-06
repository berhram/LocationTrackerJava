package com.velvet.tracker.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.core.models.cache.Cache;
import com.velvet.libs.mvi.MviViewModel;
import com.velvet.tracker.ui.state.TrackerViewEffect;
import com.velvet.tracker.ui.state.TrackerViewState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class TrackerViewModel extends MviViewModel<TrackerContract.View, TrackerViewState, TrackerViewEffect> implements TrackerContract.ViewModel {
    private final BehaviorSubject<Long> authSubject = BehaviorSubject.create();
    private final Cache locationCache;
    private final AuthNetwork authRepo;


    public TrackerViewModel(Cache locationCache, AuthNetwork authRepo) {
        this.locationCache = locationCache;
        this.authRepo = authRepo;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner owner, @NonNull Lifecycle.Event event) {
        super.onStateChanged(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    locationCache.getItem()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> {
                                if (s.isError()) {
                                setError(s.error.toString());
                            } else {
                                setLastLocation(s.data);
                            }
                        }, Throwable::printStackTrace),
                    authSubject.subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .flatMapCompletable(t -> authRepo.signOut())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::proceedToLoginScreen, Throwable::printStackTrace)
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

    private void setLastLocation(String location) {
        setState(TrackerViewState.createSetLocationState(location));
    }

    private void setError(String location) {
        setState(TrackerViewState.createSetErrorState(location));
    }

    private void proceedToLoginScreen() {
        setAction(TrackerViewEffect.proceedToLoginScreen());
    }
}
