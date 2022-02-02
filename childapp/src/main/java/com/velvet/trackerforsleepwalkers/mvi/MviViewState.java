package com.velvet.trackerforsleepwalkers.mvi;

public interface MviViewState<T extends FragmentContract.View> {
    void visit(T screen);
}