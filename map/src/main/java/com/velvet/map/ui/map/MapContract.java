package com.velvet.map.ui.map;

import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.mvi.FragmentContract;

public class MapContract {
    public interface ViewModel extends FragmentContract.ViewModel<MapViewState, MapViewEffect> {

    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();

        void setMarker(MarkerOptions marker);

        void createFilter();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
    }
}
