package com.velvet.sharedcodelibrary.mvi;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

public class FragmentContract {
    public interface ViewModel<S, E> extends LifecycleObserver {
        LiveData<S> getStateObservable();
        LiveData<E> getEffectObservable();

        void onAny(LifecycleOwner owner, Lifecycle.Event event);
    }

    public interface View {}

    public interface Host {}
}
