package com.velvet.tracker;

import com.velvet.mvi.AbstractEffect;

public class TrackerViewEffect extends AbstractEffect<TrackerContract.View> {
    private static final int ACTION_LOGIN = 1;

    private final int action;

    public TrackerViewEffect(int action) {
        this.action = action;
    }

    static public TrackerViewEffect proceedToLoginScreen() {
        return new TrackerViewEffect(ACTION_LOGIN);
    }

    @Override
    public void handle(TrackerContract.View screen) {
        if (action == ACTION_LOGIN) {
            screen.proceedToLoginScreen();
        }
    }
}
