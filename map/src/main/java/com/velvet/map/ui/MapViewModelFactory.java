package com.velvet.map.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.models.location.LocationReceiver;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MapViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    LocationReceiver receiver;

    public MapViewModelFactory() {
        AndroidInjection.inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MapViewModel.class) {
            return (T) new MapViewModel(receiver);
        }
        return null;
    }
}

