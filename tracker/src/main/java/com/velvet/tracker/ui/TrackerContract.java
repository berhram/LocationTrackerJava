package com.velvet.tracker.ui;

import com.velvet.libs.mvi.FragmentContract;
import com.velvet.tracker.ui.state.TrackerViewEffect;
import com.velvet.tracker.ui.state.TrackerViewState;

public class TrackerContract {
    public interface ViewModel extends FragmentContract.ViewModel<TrackerViewState, TrackerViewEffect> {
        void signOut();
    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();
        void setLastLocation(String text);
        void setError(String text);
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
        void startService();
        void stopService();
    }
}
