package com.velvet.map.ui.state;

import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.map.ui.MapContract;
import com.velvet.libs.mvi.MviViewState;

public class MapViewState implements MviViewState<MapContract.View> {
    private static final int ACTION_SET_LOCATIONS = 1;

    private final int action;
    private final MarkerOptions marker;

    public MapViewState(int action, MarkerOptions marker) {
        this.action = action;
        this.marker = marker;
    }

    static public MapViewState createSetMarkerState(MarkerOptions marker) {
        return new MapViewState(ACTION_SET_LOCATIONS, marker);
    }

    @Override
    public void visit(MapContract.View screen) {
        if (action == ACTION_SET_LOCATIONS) {
            screen.setMarker(marker);
        }
    }
}
