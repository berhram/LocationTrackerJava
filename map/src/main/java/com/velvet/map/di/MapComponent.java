package com.velvet.map.di;

import com.velvet.map.ui.MapViewModelFactory;
import com.velvet.models.di.BaseComponent;

import dagger.Component;

@Component(modules = {MapModule.class}, dependencies = {BaseComponent.class})
@MapScope
public interface MapComponent {
    @Component.Factory
    interface Factory {
        MapComponent create(BaseComponent component);
    }

    void inject(MapViewModelFactory factory);
}
