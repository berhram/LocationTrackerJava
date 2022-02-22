package com.velvet.core.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.velvet.core.cache.GlobalCacheImpl;
import com.velvet.core.cache.MessageCache;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.database.remote.FirebaseLocationNetwork;
import com.velvet.core.models.database.remote.LocationNetwork;
import com.velvet.core.models.location.LocationEmitter;
import com.velvet.core.models.location.LocationEmitterImpl;

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
    LocationNetwork provideLocationRepo() {
        return new FirebaseLocationNetwork(appCtx);
    }

    @Provides
    @Singleton
    WorkManager provideWM() {
        return WorkManager.getInstance(appCtx);
    }
}
