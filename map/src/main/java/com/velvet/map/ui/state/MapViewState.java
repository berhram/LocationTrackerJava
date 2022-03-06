package com.velvet.map.ui.state;

import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.map.ui.MapContract;
import com.velvet.libs.mvi.MviViewState;

public class MapViewState implements MviViewState<MapContract.View> {
    private static final int ACTION_DEFAULT = 0;
    private static final int ACTION_SET_LOCATIONS = 1;
    private static final int ACTION_SET_FILTER = 2;

    private final int action;
    private MarkerOptions marker;
    private String startDate;
    private String endDate;

    public MapViewState(int action) {
        this.action = action;
    }

    public MapViewState(int action, MarkerOptions marker) {
        this.action = action;
        this.marker = marker;
    }

    public MapViewState(int action, String startDate, String endDate) {
        this.action = action;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    static public MapViewState createSetDefaultState() {
        return new MapViewState(ACTION_DEFAULT);
    }

    static public MapViewState createSetMarkerState(MarkerOptions marker) {
        return new MapViewState(ACTION_SET_LOCATIONS, marker);
    }

    static public MapViewState createSetFilterState(String startDate, String endDate) {
        return new MapViewState(ACTION_SET_FILTER, startDate, endDate);
    }

    @Override
    public void visit(MapContract.View screen) {
        if (action == ACTION_SET_LOCATIONS) {
            screen.setMarker(marker);
        } else if (action == ACTION_SET_FILTER) {
            screen.setFilter(startDate, endDate);
        }
    }
}
