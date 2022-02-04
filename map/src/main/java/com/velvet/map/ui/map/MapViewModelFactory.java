package com.velvet.map.ui.map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.velvet.tracker.App;
import com.velvet.models.models.data.FirestoreLocationRepository;

import javax.inject.Inject;

public class MapViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @Inject
    FirestoreLocationRepository repository;

    public MapViewModelFactory() {
        App.getInstance().getComponent().inject(this);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MapViewModel.class) {
            return (T) new MapViewModel(repository);
        }
        return null;
    }
}

