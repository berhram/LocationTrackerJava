package com.velvet.core.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.velvet.core.cache.GlobalCacheImpl;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.database.local.LocalStorage;
import com.velvet.core.models.database.local.LocalStorageImpl;
import com.velvet.core.models.database.remote.FirebaseRemoteDatabase;
import com.velvet.core.models.database.remote.RemoteDatabase;
import com.velvet.core.models.location.emitter.LocationEmitter;
import com.velvet.core.models.location.emitter.LocationEmitterImpl;
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
    LocationEmitter provideEmitter() {
        return new LocationEmitterImpl(appCtx);
    }

    @Provides
    @Singleton
    FirebaseAuthNetwork provideAuthRepo() {
        return new FirebaseAuthNetwork();
    }

    @Provides
    Context provideAppContext() {
        return appCtx;
    }

    @Provides
    @Singleton
    LocalStorage provideStorage() {
        return new LocalStorageImpl(appCtx);
    }

    @Provides
    @Singleton
    RemoteDatabase provideRemoteDB() {
        return new FirebaseRemoteDatabase();
    }

    @Provides
    @Singleton
    WorkManager provideWM() {
        return WorkManager.getInstance(appCtx);
    }


}
