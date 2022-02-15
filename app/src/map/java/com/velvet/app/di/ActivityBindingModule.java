package com.velvet.app.di;

import com.velvet.map.ui.MapViewModelFactory;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract MapViewModelFactory mapViewModelFactory();
}
