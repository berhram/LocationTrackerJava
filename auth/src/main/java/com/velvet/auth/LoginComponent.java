package com.velvet.auth;

import com.velvet.auth.login.LoginViewModelFactory;

import dagger.Component;

@Component(
        modules = {LoginModule.class};
        dependencies = {AppComponent.class})

interface LoginComponent {
    void inject(LoginViewModelFactory factory);

    @Component.Builder
    interface Builder {
        Builder appComponent(AppComponent component);
        LoginComponent build();
    }
}