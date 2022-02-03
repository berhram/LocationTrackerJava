package com.velvet.parentapp.ui.map;

import com.velvet.sharedmodule.mvi.FragmentContract;

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
