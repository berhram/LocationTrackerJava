package com.velvet.trackerforsleepwalkers.mvi;

import io.reactivex.rxjava3.core.Observable;

public interface MviView<I extends MviIntent, S extends MviViewState> {
    Observable<I> intents();
    void render(S state);
}
