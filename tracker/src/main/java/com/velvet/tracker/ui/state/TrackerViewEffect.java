package com.velvet.tracker.ui.state;

import android.util.Log;

import com.velvet.libs.mvi.AbstractEffect;
import com.velvet.tracker.ui.TrackerContract;

public class TrackerViewEffect extends AbstractEffect<TrackerContract.View> {
    private static final int ACTION_LOGIN = 0;

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
            Log.d("LOC", Thread.currentThread().getName() + " current thread on effect");
            screen.proceedToLoginScreen();
        }
    }
}
