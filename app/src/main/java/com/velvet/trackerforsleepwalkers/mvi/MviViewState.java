package com.velvet.trackerforsleepwalkers.mvi;

public abstract class MviViewState<T extends FragmentContract.View> {

    public abstract void visit(T screen);
}