package com.velvet.sharedmodule.mvi;

interface MviViewEffect<T> {
    void visit(T screen);
}
