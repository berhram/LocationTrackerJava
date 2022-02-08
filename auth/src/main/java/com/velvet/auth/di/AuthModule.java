package com.velvet.auth.di;

import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {
    @Provides
    @AuthScope
    FirebaseAuthNetwork providesFirebaseAuthNetwork() {
        return new FirebaseAuthNetwork();
    }
}
