package com.velvet.libs.mvi;

public interface MviViewState<T extends FragmentContract.View> {
    void visit(T screen);
}
