package com.velvet.map.di;

import com.velvet.core.di.CoreComponent;
import com.velvet.core.di.CoreModule;
import com.velvet.libs.di.scopes.FeatureScope;
import com.velvet.map.ui.MapViewModelFactory;

import dagger.Component;

@Component(modules = {MapViewModelFactoryModule.class}, dependencies = {CoreComponent.class})
@FeatureScope
public interface MapViewModelFactoryComponent  {
    void inject(MapViewModelFactory factory);
}
