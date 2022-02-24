package com.velvet.tracker.ui.state;

import com.velvet.libs.mvi.MviViewState;
import com.velvet.tracker.ui.TrackerContract;

public class TrackerViewState implements MviViewState<TrackerContract.View> {
    private static final int ACTION_INITIAL = 1;
    private static final int ACTION_SET_LOCATION = 2;
    private static final int ACTION_SET_ERROR = 3;

    private final int action;

    private String text;

    public TrackerViewState(int action) {
        this.action = action;
    }

    public TrackerViewState(int action, String text) {
        this.action = action;
        this.text = text;
    }

    static public TrackerViewState createInitialState() {
        return new TrackerViewState(ACTION_INITIAL);
    }

    static public TrackerViewState createSetLocationState(String text) {
        return new TrackerViewState(ACTION_SET_LOCATION, text);
    }

    static public TrackerViewState createSetErrorState(String text) {
        return new TrackerViewState(ACTION_SET_LOCATION, text);
    }

    @Override
    public void visit(TrackerContract.View screen) {
        if (action == ACTION_INITIAL) {

        } else if (action == ACTION_SET_LOCATION) {
            screen.setLastLocation(text);
        } else if (action == ACTION_SET_ERROR) {
            screen.setError(text);
        }
    }
}
