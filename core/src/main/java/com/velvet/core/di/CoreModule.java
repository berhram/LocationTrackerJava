package com.velvet.core.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.velvet.core.cache.GlobalCache;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.location.LocationEmitter;
import com.velvet.core.models.location.LocationEmitterImpl;
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

    @Provides
    @Singleton
    GlobalCache<Boolean> provideCache() {
        return new MessageCache();
    }

    @Provides
    @Singleton
    FirebaseFirestore provideDB() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    @Singleton
    FirebaseAuthNetwork provideAuthRepo() {
        return new FirebaseAuthNetwork();
    }

    @Provides
    @Singleton
    FirebaseAuth provideAuthInstance() {
        return FirebaseAuth.getInstance();
    }
}
