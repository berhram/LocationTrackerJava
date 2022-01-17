package com.velvet.trackerforsleepwalkers;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.auth.AuthRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private final FirebaseAuth firebaseAuth;

    public LoginModule(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Provides
    @Singleton
    AuthNetwork providesAuthRepository() {
        return new AuthRepository(firebaseAuth);
    }
}