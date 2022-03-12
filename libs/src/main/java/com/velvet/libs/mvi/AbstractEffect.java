package com.velvet.libs.mvi;

public abstract class AbstractEffect<T extends FragmentContract.View> implements MviViewEffect<T>{
    boolean isHandled = false;

    public void visit(T screen) {
        if (!isHandled) {
            handle(screen);
            isHandled = true;
        }
    }

    public abstract void handle(T screen);
}
