package com.velvet.auth.di;

import com.velvet.auth.login.LoginViewModelFactory;
import com.velvet.core.di.CoreComponent;
import com.velvet.libs.di.scopes.FeatureScope;

import dagger.Component;

@FeatureScope
@Component(dependencies = {CoreComponent.class})
public interface LoginComponent {
    void inject(LoginViewModelFactory factory);
}
