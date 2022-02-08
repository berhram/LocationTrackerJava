package com.velvet.map.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import javax.inject.Inject;

public class MapViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    public MapViewModelFactory() {}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MapViewModel.class) {
            return (T) new MapViewModel();
        }
        return null;
    }
}

