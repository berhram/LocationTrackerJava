package com.velvet.trackerforsleepwalkers.models;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.sharedmodule.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    @Provides
    @Singleton
    FirebaseAuth providesFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    FirebaseAuthNetwork providesAuthRepository() {
        return new FirebaseAuthNetwork();
    }
}