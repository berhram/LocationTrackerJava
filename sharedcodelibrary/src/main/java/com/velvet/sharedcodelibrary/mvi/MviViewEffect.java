package com.velvet.sharedcodelibrary.mvi;

interface MviViewEffect<T> {
    void visit(T screen);
}
