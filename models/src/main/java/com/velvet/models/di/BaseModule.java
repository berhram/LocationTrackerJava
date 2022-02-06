package com.velvet.models.di;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {
    /*
    @Provides
    @Singleton
    FirebaseAuthNetwork provideFirebaseAuthNetwork() {
        return new FirebaseAuthNetwork();
    }

     */

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
