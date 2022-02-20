package com.velvet.tracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.cache.MessageCache;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.tracker.di.DaggerTrackerComponent;

import javax.inject.Inject;

public class TrackerViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    MessageCache messageCache;

    public TrackerViewModelFactory(Context ctx) {
        DaggerTrackerComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(ctx)).build().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TrackerViewModel.class) {
            return (T) new TrackerViewModel(messageCache);
        } else {
            throw new RuntimeException("Unknown class " + modelClass);
        }
    }
}
