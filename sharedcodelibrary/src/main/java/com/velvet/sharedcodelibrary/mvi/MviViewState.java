package com.velvet.sharedcodelibrary.mvi;

public interface MviViewState<T extends FragmentContract.View> {
    void visit(T screen);
}