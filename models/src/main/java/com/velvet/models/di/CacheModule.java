package com.velvet.models.di;

import com.velvet.models.cache.GlobalCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
    @Provides
    @Singleton
    GlobalCache provideCache() {
        return new GlobalCache();
    }
}
