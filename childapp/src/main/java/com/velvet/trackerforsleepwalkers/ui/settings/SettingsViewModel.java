package com.velvet.trackerforsleepwalkers.ui.settings;

import com.velvet.sharedmodule.mvi.MviViewModel;

public class SettingsViewModel extends MviViewModel<SettingsContract.View, SettingsViewState, SettingsViewEffect> implements SettingsContract.ViewModel {

    @Override
    protected SettingsViewState getDefaultState() {
        return SettingsViewState.createInitialState();
    }
}
