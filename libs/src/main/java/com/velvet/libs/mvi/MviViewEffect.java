package com.velvet.libs.mvi;

interface MviViewEffect<T> {
    void visit(T screen);
}
