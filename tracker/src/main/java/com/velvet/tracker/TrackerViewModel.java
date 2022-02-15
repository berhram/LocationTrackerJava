package com.velvet.tracker;

import com.velvet.libs.mvi.MviViewModel;
import com.velvet.tracker.state.TrackerViewEffect;
import com.velvet.tracker.state.TrackerViewState;

public class TrackerViewModel extends MviViewModel<TrackerContract.View, TrackerViewState, TrackerViewEffect> implements TrackerContract.ViewModel {

    @Override
    protected TrackerViewState getDefaultState() {
        return TrackerViewState.createInitialState();
    }
}
