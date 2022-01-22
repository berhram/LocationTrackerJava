package com.velvet.trackerforsleepwalkers.mvi;

interface MviViewEffect<T> {
    void visit(T screen);
}
