package com.velvet.auth.di;

import com.velvet.auth.login.LoginViewModelFactory;
import com.velvet.auth.passwordrecovery.PasswordRecoveryViewModelFactory;
import com.velvet.core.di.CoreComponent;

import javax.inject.Scope;

import dagger.Component;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;


@Scope
@Retention(AnnotationRetention.SOURCE)
@interface AuthScope {
}


@AuthScope
@Component(
        dependencies = {CoreComponent.class},
        modules = {AuthModule.class}
)
public interface AuthComponent {
    void inject(LoginViewModelFactory factory);

    void inject(PasswordRecoveryViewModelFactory factory);
}
