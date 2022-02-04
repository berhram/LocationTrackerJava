package com.velvet.mvi;

public interface MviViewState<T extends FragmentContract.View> {
    void visit(T screen);
}