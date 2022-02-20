package com.velvet.map.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.location.receiver.LocationReceiver;
import com.velvet.map.di.DaggerMapComponent;

import javax.inject.Inject;


public class MapViewModelFactory implements ViewModelProvider.Factory {
    @Inject
    LocationReceiver receiver;

    public MapViewModelFactory(Context ctx) {
        DaggerMapComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(ctx)).build().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MapViewModel.class) {
            return (T) new MapViewModel(receiver);
        } else {
            throw new RuntimeException("Unknown class " + modelClass);
        }
    }
}

