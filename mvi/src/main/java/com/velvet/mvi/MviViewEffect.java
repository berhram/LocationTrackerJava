package com.velvet.mvi;

interface MviViewEffect<T> {
    void visit(T screen);
}
