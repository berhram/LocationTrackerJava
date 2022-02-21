package com.velvet.tracker;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.velvet.core.cache.MessageCache;
import com.velvet.libs.mvi.MviViewModel;
import com.velvet.tracker.state.TrackerViewEffect;
import com.velvet.tracker.state.TrackerViewState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrackerViewModel extends MviViewModel<TrackerContract.View, TrackerViewState, TrackerViewEffect> implements TrackerContract.ViewModel {

    private final MessageCache messageCache;

    public TrackerViewModel(MessageCache messageCache) {
        this.messageCache = messageCache;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_CREATE && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    messageCache.getItem()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            if (s.isError()) {
                                setError(s.error.getMessage());
                            } else {
                                setLastLocation(s.data);
                            }
                        }, Throwable::printStackTrace)
            );
        }
    }

    @Override
    protected TrackerViewState getDefaultState() {
        return TrackerViewState.createInitialState();
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
