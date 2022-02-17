package com.velvet.tracker.state;

import com.velvet.libs.mvi.MviViewState;
import com.velvet.tracker.TrackerContract;

public class TrackerViewState implements MviViewState<TrackerContract.View> {
    private static final int ACTION_INITIAL = 1;
    private static final int ACTION_SET_LOCATION = 2;

    private final int action;

    private String lastLocation;

    public TrackerViewState(int action) {
        this.action = action;
    }

    public TrackerViewState(int action, String lastLocation) {
        this.action = action;
        this.lastLocation = lastLocation;
    }

    static public TrackerViewState createInitialState() {
        return new TrackerViewState(ACTION_INITIAL);
    }

    static public TrackerViewState createSetLocationState(String lastLocation) {
        return new TrackerViewState(ACTION_INITIAL, lastLocation);
    }


    @Override
    public void visit(TrackerContract.View screen) {
        if (action == ACTION_INITIAL) {

        } else if (action == ACTION_SET_LOCATION) {
            screen.setLastLocation(lastLocation);
        }
    }
}
