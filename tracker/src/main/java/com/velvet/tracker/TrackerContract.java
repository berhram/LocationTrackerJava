package com.velvet.tracker;

import com.velvet.mvi.FragmentContract;

public class TrackerContract {
    public interface ViewModel extends FragmentContract.ViewModel<TrackerViewState, TrackerViewEffect> {
    }

    public interface View extends FragmentContract.View {
        void proceedToLoginScreen();
    }

    public interface Host extends FragmentContract.Host {
        void proceedToLoginScreen(String id);
    }
}
