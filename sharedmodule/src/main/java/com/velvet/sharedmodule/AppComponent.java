package com.velvet.sharedmodule;

import com.velvet.sharedmodule.auth.FirebaseAuthNetwork;
import com.velvet.sharedmodule.ui.login.LoginViewModelFactory;
import com.velvet.sharedmodule.ui.passwordrecovery.PasswordRecoveryViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class})
@Singleton
public interface AppComponent {
    void inject(FirebaseAuthNetwork repository);

    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);
}
