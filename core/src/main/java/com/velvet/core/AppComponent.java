package com.velvet.core;

import com.velvet.models.auth.FirebaseAuthNetwork;
import com.velvet.core.ui.login.LoginViewModelFactory;
import com.velvet.core.ui.passwordrecovery.PasswordRecoveryViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class})
@Singleton
public interface AppComponent {
    void inject(FirebaseAuthNetwork repository);

    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);
}
