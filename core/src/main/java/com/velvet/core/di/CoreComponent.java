package com.velvet.core.di;

import com.velvet.core.cache.GlobalCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.location.LocationReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    LocationReceiver getLocationReceiver();

    FirebaseAuthNetwork provideAuthRepo();

    GlobalCache<Boolean> provideCache();
}
