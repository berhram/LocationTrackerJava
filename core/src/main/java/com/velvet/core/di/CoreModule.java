package com.velvet.core.di;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.velvet.core.cache.GlobalCacheImpl;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.location.receiver.LocationReceiver;
import com.velvet.core.models.location.receiver.LocationReceiverImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CoreModule {

    private final Context appCtx;

    public CoreModule(Context appCtx) {
        this.appCtx = appCtx;
    }

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

    @Provides
    @Singleton
    FirebaseAuthNetwork provideAuthRepo(Context appCtx) {
        return new FirebaseAuthNetwork(appCtx);
    }

    @Provides
    @Singleton
    FirebaseAuth provideAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    Context provideAppContext() {
        return appCtx;
    }
}
