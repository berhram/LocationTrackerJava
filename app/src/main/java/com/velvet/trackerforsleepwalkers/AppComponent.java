package com.velvet.trackerforsleepwalkers;

import com.velvet.trackerforsleepwalkers.ui.login.LoginActionProcessorHolder;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(LoginActionProcessorHolder actionProcessorHolder);
}
