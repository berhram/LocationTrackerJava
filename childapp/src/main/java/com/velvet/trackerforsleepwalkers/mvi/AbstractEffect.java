package com.velvet.trackerforsleepwalkers.mvi;

import com.velvet.trackerforsleepwalkers.ui.login.LoginContract;

public abstract class AbstractEffect<T> implements MviViewEffect<T>{
    boolean isHandled = false;

    public void visit(T screen) {
        if (!isHandled) {
            handle(screen);
            isHandled = true;
        }
    }

    public abstract void handle(T screen);
}
