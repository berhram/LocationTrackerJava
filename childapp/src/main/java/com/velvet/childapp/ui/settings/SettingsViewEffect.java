package com.velvet.childapp.ui.settings;

import com.velvet.sharedmodule.mvi.AbstractEffect;

public class SettingsViewEffect extends AbstractEffect<SettingsContract.View> {
    private static final int ACTION_MAP = 1;
    private static final int ACTION_LOGIN = 2;

    private final int action;

    public SettingsViewEffect(int action) {
        this.action = action;
    }

    static public SettingsViewEffect proceedToMapScreen() {
        return new SettingsViewEffect(ACTION_MAP);
    }

    static public SettingsViewEffect proceedToLoginScreen() {
        return new SettingsViewEffect(ACTION_LOGIN);
    }

    @Override
    public void handle(SettingsContract.View screen) {
        if (action == ACTION_MAP) {
            screen.proceedToMapScreen();
        } else if (action == ACTION_LOGIN) {
            screen.proceedToLoginScreen();
        }
    }
}
