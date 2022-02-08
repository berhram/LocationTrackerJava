package com.velvet.auth.di;

import com.velvet.auth.login.LoginViewModelFactory;
import com.velvet.auth.passwordrecovery.PasswordRecoveryViewModelFactory;
import com.velvet.models.auth.FirebaseAuthNetwork;
import com.velvet.models.di.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AuthModule.class}, dependencies = {BaseComponent.class})
@AuthScope
public interface AuthComponent {
    @Component.Factory
    interface Factory {
        AuthComponent create(BaseComponent component);
    }

    void inject(LoginViewModelFactory factory);
    void inject(PasswordRecoveryViewModelFactory factory);

}
