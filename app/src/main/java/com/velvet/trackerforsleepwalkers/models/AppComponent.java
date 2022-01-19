package com.velvet.trackerforsleepwalkers.models;

import com.velvet.trackerforsleepwalkers.models.auth.FirebaseAuthNetwork;
import com.velvet.trackerforsleepwalkers.ui.login.LoginViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class})
@Singleton
public interface AppComponent {
    void inject(FirebaseAuthNetwork repository);

    void inject(LoginViewModelFactory factory);
}
