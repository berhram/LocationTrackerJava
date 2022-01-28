package com.velvet.trackerforsleepwalkers.ui.map;

import com.velvet.trackerforsleepwalkers.mvi.FragmentContract;
import com.velvet.trackerforsleepwalkers.ui.login.LoginViewEffect;
import com.velvet.trackerforsleepwalkers.ui.login.LoginViewState;

public class MapContract {
    public interface ViewModel extends FragmentContract.ViewModel<MapViewState, MapViewEffect> {

    }

    public interface View extends FragmentContract.View {
        void proceedToSettingsScreen();

        void setMarkers();

        void createFilter();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToSettingsScreen(String id);
    }
}
