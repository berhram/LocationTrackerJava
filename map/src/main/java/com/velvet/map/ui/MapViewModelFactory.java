package com.velvet.map.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.core.di.CoreInjectHelper;
import com.velvet.core.models.location.LocationReceiver;
import com.velvet.map.di.DaggerMapViewModelFactoryComponent;

import javax.inject.Inject;


public class MapViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @Inject
    LocationReceiver receiver;

    public MapViewModelFactory(Context context) {
        DaggerMapViewModelFactoryComponent.builder().coreComponent(CoreInjectHelper.provideCoreComponent(context)).build().inject(this);
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

