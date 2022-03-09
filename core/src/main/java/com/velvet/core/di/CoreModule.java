package com.velvet.core.di;

import android.content.Context;

import androidx.work.WorkManager;

import com.velvet.core.models.auth.AuthNetwork;
import com.velvet.core.models.cache.Cache;
import com.velvet.core.models.cache.CacheImpl;
import com.velvet.core.models.auth.FirebaseAuthNetwork;
import com.velvet.core.models.database.local.LocalRepository;
import com.velvet.core.models.database.local.LocalRepositoryImpl;
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
    Cache provideCache() {
        return new CacheImpl();
    }

    @Provides
    @Singleton
    LocationEmitter provideEmitter() {
        return new LocationEmitterImpl(appCtx);
    }

    @Provides
    @Singleton
    AuthNetwork provideAuthRepo() {
        return new FirebaseAuthNetwork();
    }

    @Provides
    Context provideAppContext() {
        return appCtx;
    }

    @Provides
    @Singleton
    LocationNetwork provideRemoteRepo() {
        return new FirebaseLocationNetwork();
    }

    @Provides
    @Singleton
    LocalRepository provideLocalRepo() {
        return new LocalRepositoryImpl(appCtx);
    }

    @Provides
    @Singleton
    WorkManager provideWM() {
        return WorkManager.getInstance(appCtx);
    }
}
