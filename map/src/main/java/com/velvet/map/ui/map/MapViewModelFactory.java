package com.velvet.map.ui.map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.models.cache.ServiceCache;

import javax.inject.Inject;

public class MapViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    ServiceCache cache;

    public MapViewModelFactory() {}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MapViewModel.class) {
            return (T) new MapViewModel(cache);
        }
        return null;
    }
}

