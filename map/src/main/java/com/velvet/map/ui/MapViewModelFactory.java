package com.velvet.map.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MapViewModelFactory implements ViewModelProvider.Factory {

    public MapViewModelFactory() {

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MapViewModel.class) {
            return (T) new MapViewModel();
        } else {
            throw new RuntimeException("Unknown class " + modelClass);
        }
    }
}

