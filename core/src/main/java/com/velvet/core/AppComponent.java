package com.velvet.core;

import com.velvet.auth.login.LoginViewModelFactory;
import com.velvet.auth.passwordrecovery.PasswordRecoveryViewModelFactory;
import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    FirebaseAuthNetwork firebaseAuthNetwork();

    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);
}
