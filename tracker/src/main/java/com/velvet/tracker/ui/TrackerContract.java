package com.velvet.tracker.ui;

import com.velvet.mvi.mvi.FragmentContract;

public class TrackerContract {
    public interface ViewModel extends FragmentContract.ViewModel<TrackerViewState, TrackerViewEffect> {
    }

    public interface View extends FragmentContract.View {
        void proceedToMapScreen();

        void proceedToLoginScreen();

        void setSourceSwitch();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToMapScreen(String id);

        void proceedToLoginScreen(String id);

        void setSource(String source);
    }
}
