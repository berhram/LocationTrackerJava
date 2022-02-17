package com.velvet.core.di;

import com.google.firebase.firestore.FirebaseFirestore;
import com.velvet.core.cache.GlobalCache;
import com.velvet.core.cache.GlobalCacheImpl;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.location.emitter.LocationEmitter;
import com.velvet.core.models.location.emitter.LocationEmitterDatabase;
import com.velvet.core.models.location.emitter.LocationEmitterImpl;
import com.velvet.core.models.location.receiver.LocationReceiver;
import com.velvet.core.models.location.receiver.LocationReceiverImpl;

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
    MessageCache provideMessageCache() {
        return new MessageCache(new GlobalCacheImpl<>());
    }

    @Provides
    @Singleton
    FirebaseFirestore provideDB() {
        return FirebaseFirestore.getInstance();
    }
/*
    @Provides
    @Singleton
    LocationEmitter provideEmitter() {
        return new LocationEmitterDatabase(new LocationEmitterImpl());
    }
 */
    @Provides
    @Singleton
    FirebaseAuthNetwork provideAuthRepo() {
        return new FirebaseAuthNetwork();
    }
}
