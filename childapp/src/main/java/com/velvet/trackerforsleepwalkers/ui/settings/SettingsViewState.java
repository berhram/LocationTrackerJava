package com.velvet.trackerforsleepwalkers.ui.settings;

import com.velvet.sharedcodelibrary.mvi.MviViewState;

public class SettingsViewState implements MviViewState<SettingsContract.View> {
    private static final int ACTION_INITIAL = 1;

    private final int action;

    public SettingsViewState(int action) {
        this.action = action;
    }

    static public SettingsViewState createInitialState() {
        return new SettingsViewState(ACTION_INITIAL);
    }

    @Override
    public void visit(SettingsContract.View screen) {
        if (action == ACTION_INITIAL) {
            screen.setSourceSwitch();
        }
    }
}
