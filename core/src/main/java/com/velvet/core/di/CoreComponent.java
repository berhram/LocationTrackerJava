package com.velvet.core.di;

import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.location.receiver.LocationReceiver;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CoreModule.class})
public interface CoreComponent {
    LocationReceiver getLocationReceiver();

    FirebaseAuthNetwork provideAuthRepo();

    MessageCache provideMessageCache();

    //LocationEmitter provideEmitter();
}
