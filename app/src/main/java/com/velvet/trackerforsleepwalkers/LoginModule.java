package com.velvet.trackerforsleepwalkers;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.auth.AuthRepository;
import com.velvet.trackerforsleepwalkers.utils.schedulers.BaseSchedulerProvider;
import com.velvet.trackerforsleepwalkers.utils.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private final FirebaseAuth mFirebaseAuth;

    public LoginModule(FirebaseAuth mFirebaseAuth) {
        this.mFirebaseAuth = mFirebaseAuth;
    }

    @Provides
    @Singleton
    @NonNull
    AuthNetwork providesAuthRepository() {
        return new AuthRepository(mFirebaseAuth);
    }

    @Provides
    @Singleton
    @NonNull
    BaseSchedulerProvider providesSchedulerProvider() {
        return new SchedulerProvider();
    }
}