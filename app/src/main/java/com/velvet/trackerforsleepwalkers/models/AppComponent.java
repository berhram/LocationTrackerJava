package com.velvet.trackerforsleepwalkers.models;

import com.velvet.trackerforsleepwalkers.models.auth.FirebaseAuthNetwork;
import com.velvet.trackerforsleepwalkers.ui.login.LoginViewModelFactory;
import com.velvet.trackerforsleepwalkers.ui.passwordrecovery.PasswordRecoveryViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class})
@Singleton
public interface AppComponent {
    void inject(FirebaseAuthNetwork repository);

    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);
}
