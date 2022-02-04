package com.velvet.tracker;

import com.velvet.mvi.mvi.AbstractEffect;

public class TrackerViewEffect extends AbstractEffect<TrackerContract.View> {
    private static final int ACTION_MAP = 1;
    private static final int ACTION_LOGIN = 2;

    private final int action;

    public TrackerViewEffect(int action) {
        this.action = action;
    }

    static public TrackerViewEffect proceedToMapScreen() {
        return new TrackerViewEffect(ACTION_MAP);
    }

    static public TrackerViewEffect proceedToLoginScreen() {
        return new TrackerViewEffect(ACTION_LOGIN);
    }

    @Override
    public void handle(TrackerContract.View screen) {
        if (action == ACTION_MAP) {
            screen.proceedToMapScreen();
        } else if (action == ACTION_LOGIN) {
            screen.proceedToLoginScreen();
        }
    }
}
