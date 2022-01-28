package com.velvet.trackerforsleepwalkers.ui.settings;

import com.velvet.trackerforsleepwalkers.mvi.MviViewState;

public class SettingsViewState implements MviViewState<SettingsContract.View> {
    private static final int ACTION_ADD_NEW_FRIEND = 1;

    private static final int ACTION_HIDE = 1;

    private final int action;

    public SettingsViewState(int action) {
        this.action = action;
    }

    static public SettingsViewState createAddNewFriendState() {
        return new SettingsViewState(ACTION_ADD_NEW_FRIEND);
    }

    static public SettingsViewState createHideInviteState() {
        return new SettingsViewState(ACTION_HIDE);
    }

    @Override
    public void visit(SettingsContract.View screen) {
        if (action == ACTION_ADD_NEW_FRIEND) {

        } else if (action == ACTION_HIDE) {

        }
    }
}
