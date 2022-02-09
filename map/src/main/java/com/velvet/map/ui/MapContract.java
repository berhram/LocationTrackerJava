package com.velvet.map.ui;

import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.map.ui.state.MapViewEffect;
import com.velvet.map.ui.state.MapViewState;
import com.velvet.mvi.FragmentContract;

public class MapContract {
    public interface ViewModel extends FragmentContract.ViewModel<MapViewState, MapViewEffect> {

    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();

        void setMarker(MarkerOptions marker);

        void createFilter();

        void startService();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);

        void startService();
    }
}
