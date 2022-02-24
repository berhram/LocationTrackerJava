package com.velvet.tracker.ui.state;

import com.velvet.libs.mvi.AbstractEffect;
import com.velvet.tracker.ui.TrackerContract;

public class TrackerViewEffect extends AbstractEffect<TrackerContract.View> {
    private static final int ACTION_LOGIN = 1;
    private static final int ACTION_START_SERVICE = 2;

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
        } else if (action == ACTION_START_SERVICE) {
            screen.startService();
        }
    }
}
