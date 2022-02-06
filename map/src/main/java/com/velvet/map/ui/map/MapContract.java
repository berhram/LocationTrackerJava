package com.velvet.map.ui.map;

import com.velvet.mvi.FragmentContract;

public class MapContract {
    public interface ViewModel extends FragmentContract.ViewModel<MapViewState, MapViewEffect> {

    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();

        void setMarkers();

        void createFilter();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
    }
}
