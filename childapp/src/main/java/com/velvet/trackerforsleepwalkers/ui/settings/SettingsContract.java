package com.velvet.trackerforsleepwalkers.ui.settings;

import com.velvet.sharedmodule.mvi.FragmentContract;

public class SettingsContract {
    public interface ViewModel extends FragmentContract.ViewModel<SettingsViewState, SettingsViewEffect> {
    }

    public interface View extends FragmentContract.View {
        void proceedToMapScreen();

        void proceedToLoginScreen();

        void setSourceSwitch();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToMapScreen(String id);

        void proceedToLoginScreen(String id);

        void setSource(String source);
    }
}
