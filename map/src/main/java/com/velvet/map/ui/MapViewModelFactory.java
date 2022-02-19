package com.velvet.map.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.models.location.receiver.LocationReceiver;

import javax.inject.Inject;


public class MapViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    LocationReceiver receiver;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MapViewModel.class) {
            return (T) new MapViewModel(receiver);
        }
        return null;
    }
}

