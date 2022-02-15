package com.velvet.map.di;

import com.velvet.core.di.CoreModule;
import com.velvet.map.ui.MapViewModelFactory;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {CoreModule.class})
interface MapViewModelFactorySubcomponent extends AndroidInjector<MapViewModelFactory> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MapViewModelFactory> { }
}
