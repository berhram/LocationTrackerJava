package com.velvet.trackerforsleepwalkers.mvi;


import io.reactivex.rxjava3.core.Observable;

public interface MviViewModel <I extends MviIntent, S extends MviViewState> {
    void processIntents(Observable<I> intents);

    Observable<S> states();
}
