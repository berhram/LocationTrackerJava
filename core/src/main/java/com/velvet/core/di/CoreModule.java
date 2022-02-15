package com.velvet.core.di;

import com.velvet.core.models.location.LocationReceiver;
import com.velvet.core.models.location.LocationReceiverImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CoreModule {
    @Provides
    @Singleton
    LocationReceiver provideLocationReceiver() {
        return new LocationReceiverImpl();
    }
}
