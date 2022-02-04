package com.velvet.tracker.ui;

import com.velvet.mvi.mvi.MviViewState;

public class TrackerViewState implements MviViewState<TrackerContract.View> {
    private static final int ACTION_INITIAL = 1;

    private final int action;

    public TrackerViewState(int action) {
        this.action = action;
    }

    static public TrackerViewState createInitialState() {
        return new TrackerViewState(ACTION_INITIAL);
    }

    @Override
    public void visit(TrackerContract.View screen) {
        if (action == ACTION_INITIAL) {
            screen.setSourceSwitch();
        }
    }
}
