package com.velvet.trackerforsleepwalkers.mvi;

interface MviViewEffect<T> {
    public abstract void visit(T screen);
}
