package com.velvet.trackerforsleepwalkers.ui.login;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.auth.AuthRepository;
import com.velvet.trackerforsleepwalkers.utils.schedulers.BaseSchedulerProvider;
import com.velvet.trackerforsleepwalkers.utils.schedulers.ImmediateSchedulerProvider;
import com.velvet.trackerforsleepwalkers.utils.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    @Provides
    @Singleton
    @NonNull
    AuthNetwork providesAuthRepository() {
        return new AuthRepository(FirebaseAuth.getInstance());
    }

    @Provides
    @Singleton
    @NonNull
    BaseSchedulerProvider providesSchedulerProvider() {
        return new SchedulerProvider();
    }
/*
    @Provides
    @Singleton
    @NonNull
    BaseSchedulerProvider providesImmediateSchedulerProvider() {
        return new ImmediateSchedulerProvider();
    }

 */
}