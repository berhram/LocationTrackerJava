package com.velvet.map.ui.state;

import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.map.ui.MapContract;
import com.velvet.mvi.MviViewState;

public class MapViewState implements MviViewState<MapContract.View> {
    private static final int ACTION_CREATE_FILTER = 1;
    private static final int ACTION_SET_MARKER = 2;
    private static final int ACTION_DEFAULT = 3;

    private final int action;

    private MarkerOptions marker;

    public MapViewState(int action) {
        this.action = action;
    }

    public MapViewState(int action, MarkerOptions marker) {
        this.action = action;
        this.marker = marker;
    }


    static public MapViewState createFilterState() {
        return new MapViewState(ACTION_CREATE_FILTER);
    }

    static public MapViewState createSetMarkerState(MarkerOptions marker) {
        return new MapViewState(ACTION_SET_MARKER, marker);
    }

    static public MapViewState createSetDefaultState() {
        return new MapViewState(ACTION_DEFAULT);
    }

    @Override
    public void visit(MapContract.View screen) {
        if (action == ACTION_SET_MARKER) {
            screen.setMarker(marker);
        } else if (action == ACTION_CREATE_FILTER) {
            screen.createFilter();
        } else if (action == ACTION_DEFAULT) {
            //TODO ???
        }
    }
}
