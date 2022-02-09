package com.velvet.tracker.state;

import com.velvet.mvi.MviViewState;
import com.velvet.tracker.TrackerContract;

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
        }
    }
}
