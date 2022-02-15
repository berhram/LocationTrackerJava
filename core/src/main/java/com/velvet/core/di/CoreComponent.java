package com.velvet.core.di;

import com.velvet.core.models.location.LocationReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {CoreModule.class})
@Singleton
public interface CoreComponent {
    LocationReceiver getLocationReceiver();
}
