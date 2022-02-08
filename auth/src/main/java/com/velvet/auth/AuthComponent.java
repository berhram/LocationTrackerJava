package com.velvet.auth;

import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AuthModule.class})
@Singleton
public interface AuthComponent {
    FirebaseAuthNetwork getFirebaseAuthNetwork();
}
