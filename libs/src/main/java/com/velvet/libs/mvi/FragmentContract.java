package com.velvet.libs.mvi;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

public class FragmentContract {
    public interface ViewModel<S, E> extends LifecycleEventObserver {
        LiveData<S> getStateObservable();
        LiveData<E> getEffectObservable();

        void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event);
    }

    public interface View {}

    public interface Host {}
}
