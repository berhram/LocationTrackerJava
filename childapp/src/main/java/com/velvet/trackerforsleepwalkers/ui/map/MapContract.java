package com.velvet.trackerforsleepwalkers.ui.map;

import com.velvet.sharedcodelibrary.mvi.FragmentContract;

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
