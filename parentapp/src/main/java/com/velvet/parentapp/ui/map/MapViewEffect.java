package com.velvet.parentapp.ui.map;

import com.velvet.sharedmodule.mvi.AbstractEffect;

public class MapViewEffect extends AbstractEffect<MapContract.View> {
    private static final int ACTION_SETTINGS = 1;

    private final int action;

    public MapViewEffect(int action) {
        this.action = action;
    }

    static public MapViewEffect proceedToSettingsScreen() {
        return new MapViewEffect(ACTION_SETTINGS);
    }

    @Override
    public void handle(MapContract.View screen) {
        if (action == ACTION_SETTINGS) {
            screen.proceedToSettingsScreen();
        }
    }
}
