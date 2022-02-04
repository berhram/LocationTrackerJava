package com.velvet.map.ui.map;

import com.velvet.mvi.mvi.FragmentContract;

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
