package com.velvet.core;

import com.velvet.auth.login.LoginViewModelFactory;
import com.velvet.auth.passwordrecovery.PasswordRecoveryViewModelFactory;
import com.velvet.models.auth.FirebaseAuthNetwork;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class})
@Singleton
public interface AppComponent {
    void inject(FirebaseAuthNetwork repository);

    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);
}
