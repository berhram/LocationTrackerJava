package com.velvet.trackerforsleepwalkers;

import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.auth.AuthRepository;
import com.velvet.trackerforsleepwalkers.ui.login.LoginViewModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class})
@Singleton
public interface AppComponent {
    void inject(AuthRepository repository);

    void inject(LoginViewModelFactory factory);
}
