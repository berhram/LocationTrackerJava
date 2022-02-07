package com.velvet.models.di;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.models.auth.FirebaseAuthNetwork;
import com.velvet.models.cache.ServiceCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {
    @Provides
    @Singleton
    ServiceCache provideCache() {
        return new ServiceCache();
    }

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
