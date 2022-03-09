package com.velvet.libs.mvi;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class MviViewModel<V extends FragmentContract.View,
        S extends MviViewState,
        E extends MviViewEffect>
        extends ViewModel
        implements FragmentContract.ViewModel<S, E>,
        LifecycleEventObserver {
    private final CompositeDisposable onStopDisposables = new CompositeDisposable();
    private final CompositeDisposable onDestroyDisposables = new CompositeDisposable();
    private final MutableLiveData<S> stateHolder = new MutableLiveData<>();
    private final MutableLiveData<E> effectHolder = new MutableLiveData<>();

    @Override
    public LiveData<S> getStateObservable() {
        return stateHolder;
    }

    @Override
    public LiveData<E> getEffectObservable() {
        return effectHolder;
    }

    @CallSuper
    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_STOP) {
            onStopDisposables.clear();
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroyDisposables.clear();
        }
    }

    protected void setAction(E action) {
        effectHolder.setValue(action);
    }

    protected void observeTillStop(Disposable... subscriptions) {
        onStopDisposables.addAll(subscriptions);
    }

    protected void observeTillDestroy(Disposable... subscriptions) {
        onDestroyDisposables.addAll(subscriptions);
    }

    protected void setState(S state) {
        stateHolder.setValue(state);
    }

    protected void postState(S state) {
        stateHolder.postValue(state);
    }

    protected boolean hasOnDestroyDisposables() {
        return onDestroyDisposables.size() != 0;
    }

    protected boolean hasOStopDisposables() {
        return onStopDisposables.size() != 0;
    }

    protected S getLastState() {
        return stateHolder.getValue() == null ? getDefaultState() : stateHolder.getValue();
    }

    protected abstract S getDefaultState();
}
