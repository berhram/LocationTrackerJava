package com.velvet.trackerforsleepwalkers;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.auth.AuthRepository;

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
    AuthRepository providesAuthRepository() {
        return new AuthRepository();
    }
}