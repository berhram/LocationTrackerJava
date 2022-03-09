package com.velvet.map.ui;

import com.google.android.gms.maps.model.MarkerOptions;
import com.velvet.core.filter.DateFilter;
import com.velvet.map.ui.state.MapViewEffect;
import com.velvet.map.ui.state.MapViewState;
import com.velvet.libs.mvi.FragmentContract;

public class MapContract {
    public interface ViewModel extends FragmentContract.ViewModel<MapViewState, MapViewEffect> {
        void mapReadyCallback();

        void updateFilter(DateFilter filter);

        void signOut();
    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();

        void setErrorMessage();

        void setMarker(MarkerOptions marker);

        void setFilter(String startDate, String endDate);
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
    }
}
