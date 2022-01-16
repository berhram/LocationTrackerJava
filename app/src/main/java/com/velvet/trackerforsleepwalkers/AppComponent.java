package com.velvet.trackerforsleepwalkers;

import com.velvet.trackerforsleepwalkers.auth.AuthNetwork;
import com.velvet.trackerforsleepwalkers.ui.login.LoginActionProcessorHolder;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoginModule.class})
@Singleton
public interface AppComponent {
    void inject(LoginActionProcessorHolder loginActionProcessorHolder);

    void inject(AuthNetwork authNetwork);
}
