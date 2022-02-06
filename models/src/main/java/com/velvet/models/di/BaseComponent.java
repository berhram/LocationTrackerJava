package com.velvet.models.di;

import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {BaseModule.class})
public interface BaseComponent {
    void inject(FirebaseAuthNetwork firebaseAuthNetwork);
}
