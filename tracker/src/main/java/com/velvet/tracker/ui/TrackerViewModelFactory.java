package com.velvet.tracker.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.core.models.cache.Cache;
import com.velvet.core.di.CoreInjectHelper;
import com.velvet.tracker.di.DaggerTrackerComponent;
import com.velvet.tracker.di.TrackerModule;

import javax.inject.Inject;

public class TrackerViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    AuthNetwork repository;

    @Inject
    Cache locationCache;

    public TrackerViewModelFactory(Context ctx) {
        DaggerTrackerComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(ctx))
                .trackerModule(new TrackerModule(ctx))
                .build().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TrackerViewModel.class) {
            return (T) new TrackerViewModel(locationCache, repository);
        } else {
            throw new RuntimeException("Unknown class " + modelClass);
        }
    }
}
