package com.velvet.libs.mvi;

interface MviViewEffect<T extends FragmentContract.View> {
    void visit(T screen);
}
