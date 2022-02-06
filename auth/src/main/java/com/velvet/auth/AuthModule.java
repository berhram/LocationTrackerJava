package com.velvet.auth;

import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule {
    @Provides
    @Singleton
    FirebaseAuthNetwork providesFirebaseAuthNetwork() {
        return new FirebaseAuthNetwork();
    }
}
