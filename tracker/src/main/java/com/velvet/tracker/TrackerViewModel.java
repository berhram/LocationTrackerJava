package com.velvet.tracker;

import com.velvet.mvi.MviViewModel;

public class TrackerViewModel extends MviViewModel<TrackerContract.View, TrackerViewState, TrackerViewEffect> implements TrackerContract.ViewModel {

    @Override
    protected TrackerViewState getDefaultState() {
        return TrackerViewState.createInitialState();
    }
}
