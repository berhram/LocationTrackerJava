package com.velvet.map.di;

import com.velvet.map.ui.MapViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {MapViewModelFactorySubcomponent.class})
abstract class ViewModelFactoryModule {
    @Binds
    @IntoMap
    @ClassKey(MapViewModelFactory.class)
    abstract AndroidInjector.Factory bindMainActivityInjectorFactory(MapViewModelFactorySubcomponent.Builder builder);
}