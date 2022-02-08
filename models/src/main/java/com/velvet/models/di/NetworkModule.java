package com.velvet.models.di;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.models.cache.GlobalCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
