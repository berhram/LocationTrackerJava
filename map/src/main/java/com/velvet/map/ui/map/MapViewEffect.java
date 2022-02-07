package com.velvet.map.ui.map;

import com.velvet.mvi.AbstractEffect;

public class MapViewEffect extends AbstractEffect<MapContract.View> {
    private static final int ACTION_LOG_OUT = 1;

    private final int action;

    public MapViewEffect(int action) {
        this.action = action;
    }

    static public MapViewEffect proceedToLoginScreen() {
        return new MapViewEffect(ACTION_LOG_OUT);
    }

    @Override
    public void handle(MapContract.View screen) {
        if (action == ACTION_LOG_OUT) {
            screen.proceedToLoginScreen();
        }
    }
}
