package com.velvet.sharedmodule.mvi;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.navigation.fragment.NavHostFragment;

import java.lang.reflect.ParameterizedType;

public abstract class HostedFragment<STATE extends MviViewState,
        VIEW_MODEL extends FragmentContract.ViewModel<STATE, EFFECT>,
        HOST extends FragmentContract.Host,
        EFFECT extends MviViewEffect<VIEW>,
        VIEW extends FragmentContract.View>
        extends NavHostFragment
        implements FragmentContract.View, Observer<STATE>, LifecycleObserver {

    private VIEW_MODEL model;
    private HOST fragmentHost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentHost = (HOST) context;
        } catch (Throwable e) {
            final String hostClassName = ((Class) ((ParameterizedType) getClass().
                    getGenericSuperclass())
                    .getActualTypeArguments()[1]).getCanonicalName();
            throw new RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + this.getClass().getSimpleName(), e);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModel(createModel());
        getLifecycle().addObserver(this);
        if (getModel() != null) {
            getModel().getStateObservable().observe(this, this);
            getModel().getEffectObservable().observe(this, a -> a.visit((VIEW) this));
        }
    }

    protected abstract VIEW_MODEL createModel();

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        if (getModel() != null) {
            getModel().onAny(owner, event);
        }
        if (getLifecycle().getCurrentState().ordinal() <= Lifecycle.State.DESTROYED.ordinal()) {
            getLifecycle().removeObserver(this);
            if (getModel() != null) {
                getModel().getStateObservable().removeObservers(this);
                //TODO remove effect observer but how?
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentHost = null;
    }

    @Override
    public void onChanged(STATE screenState) {
        screenState.visit(this);
    }

    protected boolean hasHost() {
        return fragmentHost != null;
    }

    protected HOST getFragmentHost() {
        return fragmentHost;
    }

    protected VIEW_MODEL getModel() {
        return model;
    }

    protected void setModel(VIEW_MODEL model) {
        this.model = model;
    }
}