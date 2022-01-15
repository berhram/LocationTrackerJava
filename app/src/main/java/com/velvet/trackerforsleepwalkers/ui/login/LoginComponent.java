package com.velvet.trackerforsleepwalkers.ui.login;

import dagger.Component;

@Component(modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActionProcessorHolder actionProcessorHolder);
}
