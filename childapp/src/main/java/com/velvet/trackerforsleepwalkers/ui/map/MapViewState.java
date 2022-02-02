package com.velvet.trackerforsleepwalkers.ui.map;

import com.velvet.trackerforsleepwalkers.mvi.MviViewState;

public class MapViewState implements MviViewState<MapContract.View> {
    private static final int ACTION_CREATE_FILTER = 1;
    private static final int ACTION_SET_MARKERS = 2;

    private final int action;

    public MapViewState(int action) {
        this.action = action;
    }


    static public MapViewState createFilterState() {
        return new MapViewState(ACTION_CREATE_FILTER);
    }

    static public MapViewState createSetMarkersState() {
        return new MapViewState(ACTION_SET_MARKERS);
    }

    @Override
    public void visit(MapContract.View screen) {
        if (action == ACTION_SET_MARKERS) {
            screen.setMarkers();
        } else if (action == ACTION_CREATE_FILTER) {
            screen.createFilter();
        }
    }
}
