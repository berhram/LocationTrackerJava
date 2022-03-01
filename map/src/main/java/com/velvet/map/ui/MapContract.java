package com.velvet.map.ui;

import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.core.models.database.local.SimpleLocation;
import com.velvet.map.ui.state.MapViewEffect;
import com.velvet.map.ui.state.MapViewState;
import com.velvet.libs.mvi.FragmentContract;

import java.util.List;

public class MapContract {
    public interface ViewModel extends FragmentContract.ViewModel<MapViewState, MapViewEffect> {

    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();

        void postErrorMessage();

        void setMarker(MarkerOptions marker);
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
    }
}
