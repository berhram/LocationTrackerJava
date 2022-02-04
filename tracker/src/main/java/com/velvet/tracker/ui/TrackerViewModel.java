package com.velvet.tracker.ui;

import com.velvet.mvi.mvi.MviViewModel;

public class TrackerViewModel extends MviViewModel<TrackerContract.View, TrackerViewState, TrackerViewEffect> implements TrackerContract.ViewModel {

    @Override
    protected TrackerViewState getDefaultState() {
        return TrackerViewState.createInitialState();
    }
}
