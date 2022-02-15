package com.velvet.tracker;

import com.velvet.libs.mvi.FragmentContract;
import com.velvet.tracker.state.TrackerViewEffect;
import com.velvet.tracker.state.TrackerViewState;

public class TrackerContract {
    public interface ViewModel extends FragmentContract.ViewModel<TrackerViewState, TrackerViewEffect> {
    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();
        void startService();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
    }
}
