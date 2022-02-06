package com.velvet.auth;

import com.velvet.auth.login.LoginViewModelFactory;
import com.velvet.auth.passwordrecovery.PasswordRecoveryViewModelFactory;
import com.velvet.models.di.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AuthModule.class})
@Singleton
public interface AuthComponent {
    void inject(LoginViewModelFactory loginViewModelFactory);

    void inject(PasswordRecoveryViewModelFactory passwordRecoveryViewModelFactory);
}
